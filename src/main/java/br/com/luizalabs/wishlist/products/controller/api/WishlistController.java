package br.com.luizalabs.wishlist.products.controller.api;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.service.IGenerateWishlistService;
import br.com.luizalabs.wishlist.products.service.IWishlistReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@RestController
@RequestMapping("/v1/wishlist")
@Slf4j
@RequiredArgsConstructor
public class WishlistController {

    private final IGenerateWishlistService generateWishlistService;
    private final IWishlistReportService wishlistReportService;
    private final WishlistMapper wishlistMapper;

    @PostMapping
    @Operation(summary = "Call to create wish list.", tags = "wishlist")
    public Mono<ResponseEntity<WishlistDto>> createWishList(@RequestBody @Valid WishlistRequest wishlistRequest) {

        log.info("[luizalabs-wishlist-products] | Call to create wishtlist: {}.", wishlistRequest);

        return generateWishlistService.create(wishlistMapper.toWishlistFromRequest(wishlistRequest))
                .map(wishlistMapper::toWishlistDtoFrom)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(response))
                .doOnSuccess(response -> showLogFrom("Wish list created successfully", response));
    }

    @GetMapping
    @Operation(summary = "Returns a list of all wish list.", tags = "wishlist")
    public Flux<WishlistDto> findAll() {

        log.info("[luizalabs-wishlist-products] | Searching all existing wish list");
        return wishlistReportService.getAll()
                .map(wishlistMapper::toWishlistDtoFrom)
                .doOnComplete(() -> log.info("[luizalabs-wishlist-products] | Returning list of wish list"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a specific wish list by id", tags = "wishlist")
    public Mono<WishlistDto> findById(@PathVariable("id") String id) {

        log.info("[luizalabs-wishlist-products] | Searching for an existing Wish list by id");

        return wishlistReportService.getOneBy(id)
                .map(wishlistMapper::toWishlistDtoFrom)
                .doOnSuccess(p -> showLogFrom(String.format("Returning a specific wish list by {id} = %s", id), p));
    }

    private void showLogFrom(String message, ResponseEntity<WishlistDto> response) {
        log.info("[luizalabs-wishlist-products] | " + message + ": {}", response);
    }

    private void showLogFrom(String message, WishlistDto response) {
        log.info("[luizalabs-wishlist-products] | " + message + ": {}", response);
    }
}
