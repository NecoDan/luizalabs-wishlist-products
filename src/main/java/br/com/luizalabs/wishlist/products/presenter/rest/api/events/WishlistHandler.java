package br.com.luizalabs.wishlist.products.presenter.rest.api.events;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.ResponseAcceptedResponse;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.core.shared.exceptions.WishlistUnProcessableEntityException;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import br.com.luizalabs.wishlist.products.core.usecases.wishlist.service.IGenerateWishlistService;
import br.com.luizalabs.wishlist.products.core.usecases.wishlist.service.IWishlistReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WishlistHandler {

//    private final IGenerateWishlistService generateWishlistService;
//    private final IWishlistReportService wishlistReportService;
//    private final WishlistMapper wishlistMapper;
//
//    public Mono<ServerResponse> findAll(ServerRequest request) {
//
//        log.info("Searching all existing wish list(s)...");
//
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(wishlistReportService.getAll()
//                                .map(wishlistMapper::toWishlistDtoFrom)
//                                .doOnComplete(() -> log.info("Returning list of Wish list(s).")),
//                        WishlistResponse.class);
//    }
//
//    public Mono<ServerResponse> findById(ServerRequest request) {
//
//        log.info("Searching for an existing wish list by Id ...");
//
//        String id = request.pathVariable("id");
//        log.info("[luizalabs-wishlist-products] | Searching for an existing wish list by Id: {}.", id);
//
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(wishlistReportService.getOneBy(id)
//                                .map(wishlistMapper::toWishlistDtoFrom)
//                                .doOnSuccess(p -> log.info("Returning a specific wish list by {Id} =" + id)),
//                        WishlistResponse.class);
//    }
//
//    public Mono<ServerResponse> save(ServerRequest request) {
//
//        log.info("[luizalabs-wishlist-products] | Call to create wishtlist");
//
//        final Mono<Wishlist> wishlistRequest = request.bodyToMono(Wishlist.class);
//        log.info("[luizalabs-wishlist-products] | Call to create wishtlist: {}.", wishlistRequest);
//
//        final Mono<Wishlist> wishlistSave = wishlistRequest.flatMap(generateWishlistService::create)
//                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistUnProcessableEntityException(wishlistRequest.toString()))));
//
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(wishlistSave.map(wishlistMapper::toWishlistDtoFrom)
//                        .map(response -> ResponseEntity.status(HttpStatus.CREATED)
//                                .body(response))
//                        .doOnSuccess(response -> log.info("Wish list created successfully")), WishlistResponse.class);
//    }
//
//    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
//
//        log.info("[luizalabs-wishlist-products] | Remove product for an existing wish list");
//
//        String idWishlist = request.pathVariable("id_wishlist");
//        String idProduct = request.pathVariable("id_product");
//
//        log.info("[luizalabs-wishlist-products] | Remove product for an existing wish list by wishlist_id: {{}} " +
//                " and product_id: {{}}", idWishlist, idProduct);
//
//        return ServerResponse.accepted()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(generateWishlistService.removeProduct(idWishlist, idProduct)
//                                .map(wishlist -> buildResponseAcceptedDto(wishlist, idProduct))
//                                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | " +
//                                        "Product successfully removed from wishlist: {}", response)),
//                        ResponseAcceptedResponse.class);
//    }
//
//    private ResponseAcceptedResponse buildResponseAcceptedDto(Wishlist wishlist, String idProduct) {
//
//        final boolean accepted = wishlist.getItemWishlist().stream()
//                .anyMatch(i -> !StringUtils.equalsIgnoreCase(i.getProductId(), idProduct));
//
//        return buildResponseAcceptedDto(accepted);
//    }
//
//    private ResponseAcceptedResponse buildResponseAcceptedDto(boolean accepted) {
//        return ResponseAcceptedResponse.builder().build().loadContentProcessed(accepted);
//    }
}
