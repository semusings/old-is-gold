package io.github.bhuwanupadhyay.inventory.web;

import io.github.bhuwanupadhyay.inventory.command.CreateProductCommand;
import io.github.bhuwanupadhyay.inventory.v1.model.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
@Slf4j
class InventoryWebHandler {

    private final CommandGateway commandGateway;

    Mono<ServerResponse> createProduct(ServerRequest request) {
        LOG.debug("Creating product [{}]", request.attributes());
        return request.bodyToMono(CreateProductRequest.class)
                .flatMap(productRequest -> {
                    String name = productRequest.getName();
                    String id = newId();
                    CreateProductCommand command = new CreateProductCommand(id, name);
                    commandGateway.sendAndWait(command);

                    LOG.info("Created product [{}] '{}'", id, name);
                    return ok().syncBody("{ \"id\" : \"" + id + "\", \"name\" : \"" + name + "\"}");
                })
                .doOnError(AssertionError.class, e -> LOG.warn("Create Request FAILED with Message: {}", e.getMessage()))

                .onErrorResume(AssertionError.class, e -> badRequest().syncBody(e.getMessage()))

                .doOnError(CommandExecutionException.class, e -> LOG.warn("Create Command FAILED with Message: {}", e.getMessage()))

                .onErrorResume(CommandExecutionException.class, e -> badRequest().syncBody(e.getMessage()));
    }

    private String newId() {
        return UUID.randomUUID().toString();
    }

}
