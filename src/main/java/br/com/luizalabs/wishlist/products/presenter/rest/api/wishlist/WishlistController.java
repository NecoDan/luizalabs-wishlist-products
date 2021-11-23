package br.com.luizalabs.wishlist.products.presenter.rest.api.wishlist;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.core.usecases.UseCaseExecutor;
import br.com.luizalabs.wishlist.products.core.usecases.wishlist.GetAllWishlistUseCase;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.ResponseAcceptedResponse;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.WishlistResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class WishlistController implements WishlistResource {

    private final UseCaseExecutor useCaseExecutor;
    private final GetAllWishlistUseCase getAllWishlistUseCase;
    private final WishlistMapper wishlistMapper;

    @Override
    public CompletableFuture<List<Wishlist>> findAll() {

        log.info("[luizalabs-wishlist-products] | Searching all existing wish list");

        return useCaseExecutor.execute(
                getAllWishlistUseCase,
                new GetAllWishlistUseCase.InputValues(),
                GetAllWishlistUseCase.OutputValues::getWishlist);
    }

    @Override
    public CompletableFuture<Mono<WishlistResponse>> findById(String id) {
        return null;
    }

//    @GetMapping("/{id}")
//    public Mono<WishlistResponse> findByIdAlter(@PathVariable("id") String id) {
//
//        log.info("[luizalabs-wishlist-products] | Searching for an existing Wish list by id");
//
//        return wishlistReportService.getOneBy(id)
//                .map(wishlistMapper::toWishlistDtoFrom)
//                .doOnSuccess(p -> showLogFrom(String.format("Returning a specific wish list by {id} = %s", id), p));
//    }
//
//    @PostMapping
//    public Mono<ResponseEntity<WishlistResponse>> createWishList(@RequestBody @Valid WishlistRequest wishlistRequest) {
//
//        log.info("[luizalabs-wishlist-products] | Call to create wishtlist: {}.", wishlistRequest);
//
//        return generateWishlistService.create(wishlistMapper.toWishlistFromRequest(wishlistRequest))
//                .map(wishlistMapper::toWishlistDtoFrom)
//                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
//                        .body(response))
//                .doOnSuccess(response -> showLogFrom("Wish list created successfully", response));
//    }
//
//    @PutMapping("/add_product/{id_wishlist}")
//    public Mono<ResponseEntity<ResponseAcceptedResponse>> addProductBy(@Valid @PathVariable("id_wishlist") String idWishlist,
//                                                                       @Valid @RequestBody ProductRequest request) {
//
//        log.info("[luizalabs-wishlist-products] | Add product for an existing wish list by wishlist id and product request");
//
//        return generateWishlistService.addProduct(idWishlist, wishlistMapper.toWishlistRequestFrom(request))
//                .map(this::buildResponseAcceptedDtoFrom)
//                .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED)
//                        .body(response))
//                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | Product successfully add from wishlist: {}", response));
//    }
//
//    @DeleteMapping("/remove_product/{id_wishlist}")
//    public Mono<ResponseEntity<ResponseAcceptedResponse>> deleteProductBy(@Valid @PathVariable("id_wishlist") String idWishlist,
//                                                                          @Valid @RequestBody ProductRemoveRequest request) {
//
//        var idProduct = request.getIdProduct();
//        log.info("[luizalabs-wishlist-products] | Remove product for an existing wish list by wishlist_id: {{}} " +
//                " and product_id: {{}}", idWishlist, idProduct);
//
//        return generateWishlistService.removeProduct(idWishlist, idProduct)
//                .map(wishlist -> buildResponseAcceptedDto(wishlist, idProduct))
//                .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED)
//                        .body(response))
//                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | Product successfully removed from wishlist: {}", response));
//    }
//
//    @GetMapping("/find_product")
//    public Mono<ProductItemWishlistResponse> findProductBy(@Valid @RequestParam("client_id") String idClient,
//                                                           @Valid @RequestParam("product_id") String idProduct) {
//
//        log.info("[luizalabs-wishlist-products] | Searching all existing product(s) wish list by client");
//        return wishlistReportService.getProductByClientInWishlist(idClient, idProduct)
//                .doOnSuccess(p -> log.info("[luizalabs-wishlist-products] | Returning product: {}", p));
//    }


    private ResponseAcceptedResponse buildResponseAcceptedDto(boolean accepted) {
        return ResponseAcceptedResponse.builder().build().loadContentProcessed(accepted);
    }

    private ResponseAcceptedResponse buildResponseAcceptedDtoFrom(Wishlist wishlist) {
        return buildResponseAcceptedDto(true);
    }

    private ResponseAcceptedResponse buildResponseAcceptedDto(Wishlist wishlist, String idProduct) {

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
