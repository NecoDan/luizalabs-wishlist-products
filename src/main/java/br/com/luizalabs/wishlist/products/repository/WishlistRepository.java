package br.com.luizalabs.wishlist.products.repository;


import br.com.luizalabs.wishlist.products.model.Wishlist;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
public interface WishlistRepository extends ReactiveMongoRepository<Wishlist, String>, ICustomWishlistRepository {

    @Query("{ client_id: ?0, item_products: { $elemMatch: { product_id: ?1 } } }")
    Flux<Wishlist> getWishListClientIdProductId(String idClient, String idProduct);

}
