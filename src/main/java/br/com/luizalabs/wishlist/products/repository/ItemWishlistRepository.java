package br.com.luizalabs.wishlist.products.repository;


import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
public interface ItemWishlistRepository extends ReactiveMongoRepository<ItemWishlist, String> {
}
