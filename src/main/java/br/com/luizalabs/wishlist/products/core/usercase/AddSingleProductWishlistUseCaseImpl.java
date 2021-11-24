package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.core.port.GenerateWishlistPort;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class AddSingleProductWishlistUseCaseImpl implements AddSingleProductWishlistUseCase {

    private final GenerateWishlistPort generateWishlistPort;

    @Override
    public Mono<Wishlist> execute(final String idWishlist, final ItemWishlistRequest itemWishlistRequest) {

        log.info("[luizalabs-wishlist-products] | addSingleProductWishlistUseCaseImpl() | Add product for " +
                "an existing wish list by wishlist id: {} and product request: {}", idWishlist, itemWishlistRequest);
        return generateWishlistPort.addProduct(idWishlist, itemWishlistRequest);
    }

}
