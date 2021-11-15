package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IWishlistService {

    Flux<Wishlist> findAll();

    Mono<Wishlist> findById(UUID id);

    Mono<Wishlist> save(Wishlist wishlist);

    Mono<Void> update(Wishlist wishlist);

    Mono<Void> delete(UUID id);
}
