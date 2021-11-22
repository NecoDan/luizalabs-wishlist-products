package br.com.luizalabs.wishlist.products.controller.api;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.ProductRemoveRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.ProductRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.ProductItemWishlistDto;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.ResponseAcceptedDto;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.service.IGenerateWishlistService;
import br.com.luizalabs.wishlist.products.service.IWishlistReportService;
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

    private final IGenerateWishlistService generateWishlistService;
    private final IWishlistReportService wishlistReportService;
    private final WishlistMapper wishlistMapper;

    @PostMapping
    public Mono<ResponseEntity<WishlistDto>> createWishList(@RequestBody @Valid WishlistRequest wishlistRequest) {

        log.info("[luizalabs-wishlist-products] | Call to create wishtlist: {}.", wishlistRequest);

        return generateWishlistService.create(wishlistMapper.toWishlistFromRequest(wishlistRequest))
                .map(wishlistMapper::toWishlistDtoFrom)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(response))
                .doOnSuccess(response -> showLogFrom("Wish list created successfully", response));
    }

    @GetMapping
    public Flux<WishlistDto> findAll() {

        log.info("[luizalabs-wishlist-products] | Searching all existing wish list");
        return wishlistReportService.getAll()
                .map(wishlistMapper::toWishlistDtoFrom)
                .doOnComplete(() -> log.info("[luizalabs-wishlist-products] | Returning list of wish list"));
    }

    @GetMapping("/{id}")
    public Mono<WishlistDto> findById(@PathVariable("id") String id) {

        log.info("[luizalabs-wishlist-products] | Searching for an existing Wish list by id");

        return wishlistReportService.getOneBy(id)
                .map(wishlistMapper::toWishlistDtoFrom)
                .doOnSuccess(p -> showLogFrom(String.format("Returning a specific wish list by {id} = %s", id), p));
    }

    @PutMapping("/add_product/{id_wishlist}")
    public Mono<ResponseEntity<ResponseAcceptedDto>> addProductBy(@Valid @PathVariable("id_wishlist") String idWishlist,
                                                                  @Valid @RequestBody ProductRequest request) {

        log.info("[luizalabs-wishlist-products] | Add product for an existing wish list by wishlist id and product request");

        return generateWishlistService.addProduct(idWishlist, wishlistMapper.toWishlistRequestFrom(request))
                .map(this::buildResponseAcceptedDtoFrom)
                .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(response))
                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | Product successfully add from wishlist: {}", response));
    }

    @DeleteMapping("/remove_product/{id_wishlist}")
    public Mono<ResponseEntity<ResponseAcceptedDto>> deleteProductBy(@Valid @PathVariable("id_wishlist") String idWishlist,
                                                                     @Valid @RequestBody ProductRemoveRequest request) {

        var idProduct = request.getIdProduct();
        log.info("[luizalabs-wishlist-products] | Remove product for an existing wish list by wishlist_id: {{}} " +
                " and product_id: {{}}", idWishlist, idProduct);

        return generateWishlistService.removeProduct(idWishlist, idProduct)
                .map(wishlist -> buildResponseAcceptedDto(wishlist, idProduct))
                .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(response))
                .doOnSuccess(response -> log.info("[luizalabs-wishlist-products] | Product successfully removed from wishlist: {}", response));
    }

    @GetMapping("/find_product")
    public Mono<ProductItemWishlistDto> findProductBy(@Valid @RequestParam("client_id") String idClient,
                                                      @Valid @RequestParam("product_id") String idProduct) {

        log.info("[luizalabs-wishlist-products] | Searching all existing product(s) wish list by client");
        return wishlistReportService.getProductByClientInWishlist(idClient, idProduct)
                .doOnSuccess(p -> log.info("[luizalabs-wishlist-products] | Returning product: {}", p));
    }


    private ResponseAcceptedDto buildResponseAcceptedDto(boolean accepted) {
        return ResponseAcceptedDto.builder().build().loadContentProcessed(accepted);
    }

    private ResponseAcceptedDto buildResponseAcceptedDtoFrom(Wishlist wishlist) {
        return buildResponseAcceptedDto(true);
    }

    private ResponseAcceptedDto buildResponseAcceptedDto(Wishlist wishlist, String idProduct) {

        final boolean accepted = wishlist.getItemWishlist().stream()
                .anyMatch(i -> !StringUtils.equalsIgnoreCase(i.getProductId(), idProduct));

        return buildResponseAcceptedDto(accepted);
    }

    private void showLogFrom(String message, ResponseEntity<WishlistDto> response) {
        log.info("[luizalabs-wishlist-products] | " + message + ": {}", response);
    }

    private void showLogFrom(String message, WishlistDto response) {
        log.info("[luizalabs-wishlist-products] | " + message + ": {}", response);
    }
}
