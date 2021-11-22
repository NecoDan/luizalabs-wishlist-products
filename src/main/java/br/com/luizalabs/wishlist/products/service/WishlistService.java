package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.repository.WishlistRepository;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistNotFoundException;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistUnProcessableEntityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
        return Flux.fromIterable(this.wishlistRepository.findAll())
                .switchIfEmpty(Mono.defer(()
                        -> Mono.error(new WishlistNotFoundException("collection of data from wishlists not found"))));
    }

    @Override
    public Mono<Wishlist> findById(String id) {
        return Mono.just(this.wishlistRepository.findById(id)
                .orElseThrow(() -> new WishlistNotFoundException("", id)))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistNotFoundException("", id))));
    }

    @Override
    public Mono<Wishlist> getOneSavedById(String id) {
        return Mono.just(this.wishlistRepository.findById(id)
                .orElseThrow(() -> new WishlistNotFoundException("", id)))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistUnProcessableEntityException(id))));
    }

    @Override
    @Transactional
    public Mono<Wishlist> save(Wishlist wishlist) {
        return Mono.just(this.wishlistRepository.save(wishlist));
    }

    @Override
    @Transactional
    public Mono<Void> update(Wishlist wishlist) {
        return findById(wishlist.getId())
                .flatMap(this::save)
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> delete(String id) {
        this.wishlistRepository.deleteById(id);
        return Mono.empty();
    }

    @Override
    public Flux<Wishlist> findAllByClientId(String idClient) {
        return Flux.fromIterable(this.wishlistRepository.findAllByClientId(idClient));
    }

    public List<Wishlist> findAllWishListClientIdAndProductId(String idClient, String idProduct) {
        return this.wishlistRepository.findAllWishListClientIdAndProductId(idClient, idProduct);
    }
}
