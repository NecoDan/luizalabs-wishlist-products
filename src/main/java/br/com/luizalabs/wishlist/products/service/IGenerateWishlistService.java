package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.dto.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import reactor.core.publisher.Mono;

public interface IGenerateWishlistService {

    Mono<Wishlist> create(final Wishlist wishlist);

    Mono<Wishlist> addProduct(final String idWishlist, final ItemWishlistRequest itemWishlistRequest);

    Mono<Wishlist> removeProduct(final String idWishlist, final String idProduct);

    Mono<Wishlist> perfomProductRemoval(final Wishlist wishlist, final String idProduct);

    Mono<Wishlist> performRemoveProduct(final Wishlist wishlist, final ItemWishlist itemWishlist);
}
