package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ProductItemWishlistResponse;
import br.com.luizalabs.wishlist.products.core.biz.WishlistReportBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class GetOneProductByClientIdProductIdUseCaseImpl implements GetOneProductByClientIdProductIdUseCase {

    private final WishlistReportBusiness wishlistReportBusiness;

    @Override
    public Mono<ProductItemWishlistResponse> execute(String clientId, String productId) {

        log.info("[luizalabs-wishlist-products] | get one product by client id and product id | " +
                "Searching one existing product wish list by parameters");
        return wishlistReportBusiness.getProductByClientInWishlist(clientId, productId);
    }
}
