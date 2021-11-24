package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ProductItemWishlistResponse;
import reactor.core.publisher.Mono;

public interface GetOneProductByClientIdProductIdUseCase {

    Mono<ProductItemWishlistResponse> execute(final String clientId, final String productId);

}
