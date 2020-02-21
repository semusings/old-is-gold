package io.github.bhuwanupadhyay.inventory.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
class InventoryRoutes {

    private final InventoryWebHandler handler;

    @Bean
    RouterFunction<ServerResponse> routes() {
        return nest(
                path("/inventory"),
                nest(
                        accept(APPLICATION_JSON),
                        route(POST("/products")
                                .and(contentType(APPLICATION_JSON)), handler::createProduct)
                                .and(route(GET("/"), req -> ok().syncBody("{ \"status\" : \"UP\"}")))
                )
        );
    }


}
