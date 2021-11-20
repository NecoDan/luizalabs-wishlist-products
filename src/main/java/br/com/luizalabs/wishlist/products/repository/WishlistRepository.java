package br.com.luizalabs.wishlist.products.repository;


import br.com.luizalabs.wishlist.products.model.Wishlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
public interface WishlistRepository extends ReactiveMongoRepository<Wishlist, String>, ICustomWishlistRepository {

    Flux<Wishlist> findAllByClientId(@Param("clientId") String clientId);

}
