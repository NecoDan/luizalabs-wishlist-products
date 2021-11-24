package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import reactor.core.publisher.Mono;

public interface PostOneWishlistUseCase {

    Mono<Wishlist> execute(final Wishlist wishlist);
}
