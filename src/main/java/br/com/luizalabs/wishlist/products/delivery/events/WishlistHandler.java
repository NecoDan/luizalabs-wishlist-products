package br.com.luizalabs.wishlist.products.delivery.events;

import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ResponseAccepted;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.shared.WishlistMapper;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistUnProcessableEntityException;
import br.com.luizalabs.wishlist.products.core.usercase.GetAllWishlistUseCase;
import br.com.luizalabs.wishlist.products.core.usercase.GetOneByIdWishlistUseCase;
import br.com.luizalabs.wishlist.products.core.port.GenerateWishlistPort;
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

    private final GenerateWishlistPort generateWishlistService;
    private final GetAllWishlistUseCase getAllWishlistUseCase;
    private final GetOneByIdWishlistUseCase getOneByIdWishlistUseCase;
    private final WishlistMapper wishlistMapper;

    public Mono<ServerResponse> findAll(ServerRequest request) {

        log.info("Searching all existing wish list(s)...");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getAllWishlistUseCase.execute()
                                .map(wishlistMapper::toWishlistResponseFrom)
                                .doOnComplete(() -> log.info("Returning list of Wish list(s).")),
                        WishlistResponse.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {

        log.info("Searching for an existing wish list by Id ...");

        String id = request.pathVariable("id");
        log.info("[luizalabs-wishlist-products] | Searching for an existing wish list by Id: {}.", id);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getOneByIdWishlistUseCase.execute(id)
                                .map(wishlistMapper::toWishlistResponseFrom)
                                .doOnSuccess(p -> log.info("Returning a specific wish list by {Id} =" + id)),
                        WishlistResponse.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {

        log.info("[luizalabs-wishlist-products] | Call to create wishtlist");

        final Mono<Wishlist> wishlistRequest = request.bodyToMono(Wishlist.class);
        log.info("[luizalabs-wishlist-products] | Call to create wishtlist: {}.", wishlistRequest);

        final Mono<Wishlist> wishlistSave = wishlistRequest.flatMap(generateWishlistService::create)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistUnProcessableEntityException(wishlistRequest.toString()))));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(wishlistSave.map(wishlistMapper::toWishlistResponseFrom)
                        .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                                .body(response))
                        .doOnSuccess(response -> log.info("Wish list created successfully")), WishlistResponse.class);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {

        log.info("[luizalabs-wishlist-products] | Remove product for an existing wish list");

        String idWishlist = request.pathVariable("id_wishlist");
        String idProduct = request.pathVariable("id_product");

        log.info("[luizalabs-wishlist-products] | Remove product for an existing wish list by wishlist_id: {{}} " +
                " and product_id: {{}}", idWishlist, idProduct);

        return ServerResponse.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateWishlistService.removeProduct(idWishlist, idProduct)
                                .map(wishlist -> buildResponseAcceptedDto(wishlist, idProduct))
                                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | " +
                                        "Product successfully removed from wishlist: {}", response)),
                        ResponseAccepted.class);
    }

    private ResponseAccepted buildResponseAcceptedDto(Wishlist wishlist, String idProduct) {

        final boolean accepted = wishlist.getItemWishlist().stream()
                .anyMatch(i -> !StringUtils.equalsIgnoreCase(i.getProductId(), idProduct));

        return buildResponseAcceptedDto(accepted);
    }

    private ResponseAccepted buildResponseAcceptedDto(boolean accepted) {
        return ResponseAccepted.builder().build().loadContentProcessed(accepted);
    }
}
