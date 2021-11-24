package br.com.luizalabs.wishlist.products.delivery.wishlist;


import br.com.luizalabs.wishlist.products.core.usercase.*;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ProductRemoveRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ProductRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ProductItemWishlistResponse;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ResponseAccepted;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.shared.WishlistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class WishlistController {

    private final GetAllWishlistUseCase getAllWishlistUseCase;
    private final GetOneByIdWishlistUseCase getOneByIdWishlistUseCase;
    private final GetOneProductByClientIdProductIdUseCase getOneProductByClientIdProductIdUseCase;
    private final PostOneWishlistUseCase postOneWishlistUseCase;
    private final AddSingleProductWishlistUseCase addSingleProductWishlistUseCase;
    private final DeleteProductWishlistUseCase deleteProductWishlistUseCase;
    private final WishlistMapper wishlistMapper;

    @PostMapping
    public Mono<ResponseEntity<WishlistResponse>> createWishList(@RequestBody @Valid WishlistRequest wishlistRequest) {

        log.info("[luizalabs-wishlist-products] | Call to create wishtlist: {}.", wishlistRequest);

        return postOneWishlistUseCase.execute(wishlistMapper.toWishlistFromRequest(wishlistRequest))
                .map(wishlistMapper::toWishlistResponseFrom)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(response))
                .doOnSuccess(response -> showLogFrom("Wish list created successfully", response));
    }

    @GetMapping
    public Flux<WishlistResponse> findAll() {

        log.info("[luizalabs-wishlist-products] | Searching all existing wish list");
        return getAllWishlistUseCase.execute()
                .map(wishlistMapper::toWishlistResponseFrom)
                .doOnComplete(() -> log.info("[luizalabs-wishlist-products] | Returning list of wish list"));
    }

    @GetMapping("/{id}")
    public Mono<WishlistResponse> findById(@PathVariable("id") String id) {

        log.info("[luizalabs-wishlist-products] | Searching for an existing Wish list by id");

        return getOneByIdWishlistUseCase.execute(id)
                .map(wishlistMapper::toWishlistResponseFrom)
                .doOnSuccess(p -> showLogFrom(String.format("Returning a specific wish list by {id} = %s", id), p));
    }

    @PutMapping("/add_product/{id_wishlist}")
    public Mono<ResponseEntity<ResponseAccepted>> addProductBy(@Valid @PathVariable("id_wishlist") String idWishlist,
                                                               @Valid @RequestBody ProductRequest request) {

        log.info("[luizalabs-wishlist-products] | Add product for an existing wish list by wishlist id and product request");

        return addSingleProductWishlistUseCase.execute(idWishlist, wishlistMapper.toItemWishlistRequestFrom(request))
                .map(this::buildResponseAcceptedDtoFrom)
                .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(response))
                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | Product successfully add from wishlist: {}", response));
    }

    @DeleteMapping("/remove_product/{id_wishlist}")
    public Mono<ResponseEntity<ResponseAccepted>> deleteProductBy(@Valid @PathVariable("id_wishlist") String idWishlist,
                                                                  @Valid @RequestBody ProductRemoveRequest request) {

        var idProduct = request.getIdProduct();
        log.info("[luizalabs-wishlist-products] | Remove product for an existing wish list by wishlist_id: {{}} " +
                " and product_id: {{}}", idWishlist, idProduct);

        return deleteProductWishlistUseCase.execute(idWishlist, idProduct)
                .map(wishlist -> buildResponseAcceptedDto(wishlist, idProduct))
                .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(response))
                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | Product successfully removed from wishlist: {}", response));
    }

    @GetMapping("/find_product")
    public Mono<ProductItemWishlistResponse> findProductBy(@Valid @RequestParam("client_id") String clientId,
                                                           @Valid @RequestParam("product_id") String productId) {

        log.info("[luizalabs-wishlist-products] | Searching all existing product(s) wish list by client");
        return getOneProductByClientIdProductIdUseCase.execute(clientId, productId)
                .doOnSuccess(p -> log.info("[luizalabs-wishlist-products] | Returning product: {}", p));
    }

    private ResponseAccepted buildResponseAcceptedDto(boolean accepted) {
        return ResponseAccepted.builder().build().loadContentProcessed(accepted);
    }

    private ResponseAccepted buildResponseAcceptedDtoFrom(Wishlist wishlist) {
        return buildResponseAcceptedDto(true);
    }

    private ResponseAccepted buildResponseAcceptedDto(Wishlist wishlist, String idProduct) {

        final boolean accepted = wishlist.getItemWishlist().stream()
                .anyMatch(i -> !StringUtils.equalsIgnoreCase(i.getProductId(), idProduct));

        return buildResponseAcceptedDto(accepted);
    }

    private void showLogFrom(String message, ResponseEntity<WishlistResponse> response) {
        log.info("[luizalabs-wishlist-products] | " + message + ": {}", response);
    }

    private void showLogFrom(String message, WishlistResponse response) {
        log.info("[luizalabs-wishlist-products] | " + message + ": {}", response);
    }
}
