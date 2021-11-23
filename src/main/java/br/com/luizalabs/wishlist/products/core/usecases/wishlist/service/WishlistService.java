package br.com.luizalabs.wishlist.products.core.usecases.wishlist.service;

import br.com.luizalabs.wishlist.products.core.usecases.wishlist.repository.WishlistRepository;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
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
        return this.wishlistRepository.findAll();
    }

    @Override
    public Mono<Wishlist> findById(String id) {
        return this.wishlistRepository.findById(id);
    }

    @Override
    @Transactional
    public Mono<Wishlist> save(Wishlist wishlist) {
        return this.wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public Mono<Void> update(Wishlist wishlist) {
        return this.wishlistRepository.update(wishlist);
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.wishlistRepository.delete(id);
    }

    @Override
    public Flux<Wishlist> findAllByClientId(String idClient) {
        return this.wishlistRepository.findAllByClientId(idClient);
    }

    @Override
    public List<Wishlist> findAllWishListClientIdAndProductId(String idClient, String idProduct) {
        return this.wishlistRepository.findAllWishListClientIdAndProductId(idClient, idProduct);
    }

    @Override
    public Flux<Wishlist> findWishListClientIdAndProductId(String idClient, String idProduct) {
        return this.wishlistRepository.findWishListClientIdAndProductId(idClient, idProduct);
    }
}
