package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IWishlistService {

    Flux<Wishlist> findAll();

    Mono<Wishlist> getOneBy(String id);

    Mono<Wishlist> findById(String id);

    Flux<Wishlist> findByClientId(String idClient, String idProduct);

    Mono<Wishlist> save(Wishlist wishlist);

    Mono<Void> update(Wishlist wishlist);

    Mono<Void> delete(String id);
}
