package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.exceptions.WishlistNotFoundException;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WishlistService implements IWishlistService {

    private final WishlistRepository wishlistRepository;

    @Override
    public Flux<Wishlist> findAll() {
        return this.wishlistRepository.findAll();
    }

    @Override
    public Mono<Wishlist> findById(UUID id) {
        return this.wishlistRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistNotFoundException(id.toString()))));
    }

    @Override
    public Mono<Wishlist> save(Wishlist wishlist) {
        return this.wishlistRepository.save(wishlist);
    }

    @Override
    public Mono<Void> update(Wishlist wishlist) {
        return findById(wishlist.getId())
                .flatMap(wishlistRepository::save)
                .then();
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return findById(id)
                .flatMap(wishlistRepository::delete);
    }
}
