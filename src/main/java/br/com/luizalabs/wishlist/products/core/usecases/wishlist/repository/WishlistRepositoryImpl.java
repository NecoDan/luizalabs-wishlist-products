package br.com.luizalabs.wishlist.products.core.usecases.wishlist.repository;

import br.com.luizalabs.wishlist.products.core.shared.exceptions.WishlistNotFoundException;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import br.com.luizalabs.wishlist.products.data.db.jpa.repositories.JpaWishlistRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class WishlistRepositoryImpl implements WishlistRepository {

    private JpaWishlistRepository repository;

    public WishlistRepositoryImpl(JpaWishlistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Wishlist> findAll() {
        return Flux.fromIterable(this.repository.findAll())
                .switchIfEmpty(Mono.defer(()
                        -> Mono.error(new WishlistNotFoundException("collection of data from wishlists not found"))));
    }

    @Override
    public Mono<Wishlist> findById(String id) {
        return Mono.just(this.repository.findById(id)
                .orElseThrow(() -> new WishlistNotFoundException("", id)))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistNotFoundException("", id))));
    }

    @Override
    @Transactional
    public Mono<Wishlist> save(Wishlist wishlist) {
        return Mono.just(this.repository.save(wishlist));
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
        this.repository.deleteById(id);
    }

    @Override
    public Flux<Wishlist> findAllByClientId(String idClient) {
        return Flux.fromIterable(this.repository.findAllByClientId(idClient));
    }

    @Override
    public List<Wishlist> findAllWishListClientIdAndProductId(String idClient, String idProduct) {
        return this.repository.findAllWishListClientIdAndProductId(idClient, idProduct);
    }

    @Override
    public Flux<Wishlist> findWishListClientIdAndProductId(String idClient, String idProduct) {
        return Flux.fromIterable(findAllWishListClientIdAndProductId(idClient, idProduct));
    }

}
