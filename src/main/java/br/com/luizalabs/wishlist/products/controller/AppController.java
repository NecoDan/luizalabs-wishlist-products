package br.com.luizalabs.wishlist.products.controller;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.model.ProductItemWishlist;
import br.com.luizalabs.wishlist.products.service.IWishlistReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 * Home redirection to swagger api documentation
 */
@RestController
@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AppController {

    @Value("${app.message}")
    private String appMessage;

    private final IWishlistReportService wishlistReportService;
    private final WishlistMapper wishlistMapper;

    @GetMapping
    public ResponseEntity<String> getAppMessage() {
        log.info("[luizalabs-wishlist-products] [events] | {}", appMessage);
        return ResponseEntity.ok(appMessage);
    }

    @GetMapping("/products/find_products")
    public Mono<ProductItemWishlist> findProductPorClient(@RequestParam("client_id") String idClient,
                                                          @RequestParam("product_id") String idProduct) {

        log.info("[luizalabs-wishlist-products] | Searching all existing product(s) wish list by client");
        return wishlistReportService.getProductByClientInWishlist(idClient, idProduct)
                .doOnSuccess(p -> log.info("[luizalabs-wishlist-products] | Returning product: {}", p));
    }
}
