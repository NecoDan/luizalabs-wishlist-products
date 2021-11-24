package br.com.luizalabs.wishlist.products.core.port;

import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface GenerateWishlistPort {

    Mono<Wishlist> create(final Wishlist wishlist);

    Mono<Wishlist> perfomSaveWishlist(Wishlist wishlist);

    Mono<Wishlist> addProduct(final String idWishlist, final ItemWishlistRequest itemWishlistRequest);

    Mono<Wishlist> perfomProductAdd(final Wishlist wishlist, final ItemWishlistRequest itemWishlistRequest);

    Mono<Wishlist> removeProduct(final String idWishlist, final String idProduct);

    Mono<Wishlist> perfomProductRemoval(final Wishlist wishlist, final String idProduct);

    Mono<Wishlist> performRemoveProduct(final Wishlist wishlist, final ItemWishlist itemWishlist);
}
