package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.exceptions.WishlistNotFoundException;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.repository.ItemWishlistRepository;
import br.com.luizalabs.wishlist.products.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Wishlist> findById(String id) {
        return this.wishlistRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistNotFoundException(id))));
    }

    @Override
    public Flux<Wishlist> findByClientId(String idClient) {
        return null;
    }

    @Override
    @Transactional
    public Mono<Wishlist> save(Wishlist wishlist) {
        return this.wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public Mono<Void> update(Wishlist wishlist) {
        return findById(wishlist.getId())
                .flatMap(wishlistRepository::save)
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> delete(String id) {
        return findById(id).flatMap(wishlistRepository::delete);
    }

    @Override
    public Long totalElementsWishList(String id) {
        return 0L;
    }
}
