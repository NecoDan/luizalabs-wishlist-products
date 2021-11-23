package br.com.luizalabs.wishlist.products.data.db.jpa.repositories;


import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
public interface JpaWishlistRepository extends MongoRepository<Wishlist, String> {

    List<Wishlist> findAllByClientId(@Param("client_id") String clientId);

    @Query("{ client_id: ?0, itens_products: { $elemMatch: { product_id: ?1 } } }")
    List<Wishlist> findAllWishListClientIdAndProductId(@Param("idClient") String idClient, @Param("idProduct") String idProduct);

}
