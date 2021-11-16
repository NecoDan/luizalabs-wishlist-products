package br.com.luizalabs.wishlist.products.controller.events;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
public class WishlistRouter {

    private static final String URI = "/v1/events/wishlist";

    @Bean
    public RouterFunction<ServerResponse> routeVoters(WishlistHandler handler) {
        return RouterFunctions
                .route(GET(URI)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(URI + "/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::findById)
//                .andRoute(DELETE(URI + "/{id_wishlist}/products/remove/{id_product}")
//                        .and(accept(MediaType.APPLICATION_JSON)), handler::deleteProduct)
                .andRoute(POST(URI)
                        .and(accept(MediaType.APPLICATION_JSON)), handler::save);
    }
}
