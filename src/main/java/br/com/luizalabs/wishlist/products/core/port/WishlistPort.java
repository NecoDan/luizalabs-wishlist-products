package br.com.luizalabs.wishlist.products.core.port;

import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface WishlistPort {

    Flux<Wishlist> findAll();

    Mono<Wishlist> findById(final String id);

    Mono<Wishlist> save(final Wishlist wishlist);

    Mono<Void> update(final Wishlist wishlist);

    void delete(final String id);

    Flux<Wishlist> findAllByClientId(final String idClient);

    List<Wishlist> findAllWishListClientIdAndProductId(String idClient, String idProduct);

    Flux<Wishlist> findWishListClientIdAndProductId(String idClient, String idProduct);
}
