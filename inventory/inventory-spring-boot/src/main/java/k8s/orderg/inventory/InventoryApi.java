package k8s.orderg.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@RequiredArgsConstructor
public class InventoryApi {

    private static final String BASE_PATH = "/api/inventory/products";
    private static final String PRODUCT_REF_NO_PATH_VARIABLE = "{product-ref-no}";
    private static final String ONE_PRODUCT = BASE_PATH + "/" + PRODUCT_REF_NO_PATH_VARIABLE;

    private final InventoryService inventory;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route(GET(BASE_PATH), this::getAllProducts)
                .andRoute(GET(ONE_PRODUCT), this::getOneProductByRefNo)
                .andRoute(POST(BASE_PATH).and(contentType(APPLICATION_JSON)), this::createProduct)
                .andRoute(PUT(ONE_PRODUCT).and(contentType(APPLICATION_JSON)), this::updateProduct)
                .andRoute(DELETE(ONE_PRODUCT), this::deleteProduct);
    }

    private Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return inventory.
                list().
                collectList().
                flatMap(inventoryItems -> ServerResponse.ok().body(Flux.fromIterable(inventoryItems), InventoryItem.class));
    }

    private Mono<ServerResponse> getOneProductByRefNo(ServerRequest request) {
        return Mono.justOrEmpty(null);
    }

    private Mono<ServerResponse> createProduct(ServerRequest request) {
        return Mono.justOrEmpty(null);
    }

    private Mono<ServerResponse> updateProduct(ServerRequest request) {
        return Mono.justOrEmpty(null);
    }

    private Mono<ServerResponse> deleteProduct(ServerRequest request) {
        return Mono.justOrEmpty(null);
    }

}