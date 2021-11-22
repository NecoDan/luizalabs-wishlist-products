package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IWishlistService {

    Flux<Wishlist> findAll();

    Mono<Wishlist> findById(final String id);

    Mono<Wishlist> getOneSavedById(final String id);

    Mono<Wishlist> save(final Wishlist wishlist);

    Mono<Void> update(final Wishlist wishlist);

    Mono<Void> delete(final String id);

    Flux<Wishlist> findAllByClientId(final String idClient);
}
