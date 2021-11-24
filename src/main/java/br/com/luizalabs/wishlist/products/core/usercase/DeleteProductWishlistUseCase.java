package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import reactor.core.publisher.Mono;

public interface DeleteProductWishlistUseCase {
    Mono<Wishlist> execute(String idWishlist, String idProduct);
}
