package br.com.luizalabs.wishlist.products.core.biz;

import br.com.luizalabs.wishlist.products.core.port.WishlistPort;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.data.repositories.WishlistRepository;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistNotFoundException;
import lombok.AllArgsConstructor;
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
@Service
@AllArgsConstructor
public class WishlistBusiness implements WishlistPort {

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
    public void delete(String id) {
        this.wishlistRepository.deleteById(id);
    }

    @Override
    public Flux<Wishlist> findAllByClientId(String idClient) {
        return Flux.fromIterable(this.wishlistRepository.findAllByClientId(idClient));
    }

    @Override
    public List<Wishlist> findAllWishListClientIdAndProductId(String idClient, String idProduct) {
        return this.wishlistRepository.findAllWishListClientIdAndProductId(idClient, idProduct);
    }

    @Override
    public Flux<Wishlist> findWishListClientIdAndProductId(String idClient, String idProduct) {
        return Flux.fromIterable(findAllWishListClientIdAndProductId(idClient, idProduct));
    }
}
