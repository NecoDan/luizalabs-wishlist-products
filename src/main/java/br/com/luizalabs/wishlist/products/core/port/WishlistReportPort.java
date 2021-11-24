package br.com.luizalabs.wishlist.products.core.port;

import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ProductItemWishlistResponse;
import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public interface WishlistReportPort {

    Flux<Wishlist> getAll();

    Mono<Wishlist> getOneBy(String id);

    Mono<ProductItemWishlistResponse> getProductByClientInWishlist(String idClient, String idProduct);

    Optional<ItemWishlist> getItemWishlistByProductsId(List<Wishlist> wishlists, String idProduct);
}
