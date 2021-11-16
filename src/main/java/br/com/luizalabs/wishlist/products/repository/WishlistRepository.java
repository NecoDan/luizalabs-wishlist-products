package br.com.luizalabs.wishlist.products.repository;


import br.com.luizalabs.wishlist.products.model.Wishlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
public interface WishlistRepository extends ReactiveMongoRepository<Wishlist, String> {
}
