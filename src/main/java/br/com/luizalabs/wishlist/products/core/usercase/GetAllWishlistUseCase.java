package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import reactor.core.publisher.Flux;

public interface GetAllWishlistUseCase {

    Flux<Wishlist> execute();
}
