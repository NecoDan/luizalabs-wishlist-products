package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import reactor.core.publisher.Mono;

public interface AddSingleProductWishlistUseCase {

    Mono<Wishlist> execute(String idWishlist, ItemWishlistRequest itemWishlistRequest);
}
