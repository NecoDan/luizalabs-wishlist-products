package br.com.luizalabs.wishlist.products.core.usecases.wishlist.service;

import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import reactor.core.publisher.Mono;

public interface IGenerateWishlistService {

    Mono<Wishlist> create(final Wishlist wishlist);

    Mono<Wishlist> perfomSaveWishlist(Wishlist wishlist);

    Mono<Wishlist> addProduct(final String idWishlist, final ItemWishlistRequest itemWishlistRequest);

    Mono<Wishlist> perfomProductAdd(final Wishlist wishlist, final ItemWishlistRequest itemWishlistRequest);

    Mono<Wishlist> removeProduct(final String idWishlist, final String idProduct);

    Mono<Wishlist> perfomProductRemoval(final Wishlist wishlist, final String idProduct);

    Mono<Wishlist> performRemoveProduct(final Wishlist wishlist, final ItemWishlist itemWishlist);
}
