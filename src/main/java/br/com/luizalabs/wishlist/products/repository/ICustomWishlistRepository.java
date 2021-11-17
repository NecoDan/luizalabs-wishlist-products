package br.com.luizalabs.wishlist.products.repository;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import reactor.core.publisher.Flux;

public interface ICustomWishlistRepository {

    Flux<Wishlist> findAllByClientId(String idClient);
}
