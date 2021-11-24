package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.core.port.GenerateWishlistPort;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class DeleteProductWishlistUseCaseImpl implements DeleteProductWishlistUseCase {

    private final GenerateWishlistPort generateWishlistPort;

    @Override
    public Mono<Wishlist> execute(final String idWishlist, final String idProduct) {

        return generateWishlistPort.removeProduct(idWishlist, idProduct);
    }

}
