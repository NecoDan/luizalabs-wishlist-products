package br.com.luizalabs.wishlist.products.controller.events;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.exceptions.WishlistUnprocessableEntityException;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.service.IGenerateWishlistService;
import br.com.luizalabs.wishlist.products.service.IWishlistReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final IGenerateWishlistService generateWishlistService;
    private final IWishlistReportService wishlistReportService;
    private final WishlistMapper wishlistMapper;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        log.info("Searching all existing wish list(s)...");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(wishlistReportService.getAll()
                                .map(wishlistMapper::toWishlistDtoFrom)
                                .doOnComplete(() -> log.info("Returning list of Wish list(s).")),
                        WishlistDto.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        log.info("Searching for an existing wish list by Id ...");
        String id = request.pathVariable("id");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(wishlistReportService.getOneBy(id)
                                .map(wishlistMapper::toWishlistDtoFrom)
                                .doOnSuccess(p -> log.info("Returning a specific wish list by {Id} =" + id)),
                        WishlistDto.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        final Mono<Wishlist> wishlist = request.bodyToMono(Wishlist.class);
        final Mono<Wishlist> wishlistSave = wishlist.flatMap(generateWishlistService::create)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistUnprocessableEntityException(wishlist.toString()))));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(wishlistSave.map(wishlistMapper::toWishlistDtoFrom)
                        .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                                .body(response))
                        .doOnSuccess(response -> log.info("Wish list created successfully")), WishlistDto.class);
    }
}
