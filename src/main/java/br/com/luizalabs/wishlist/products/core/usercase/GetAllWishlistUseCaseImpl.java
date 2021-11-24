package br.com.luizalabs.wishlist.products.core.usercase;


import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.core.biz.WishlistReportBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@AllArgsConstructor
public class GetAllWishlistUseCaseImpl implements GetAllWishlistUseCase {

    private final WishlistReportBusiness wishlistReportBusiness;

    @Override
    public Flux<Wishlist> execute() {

        log.info("[luizalabs-wishlist-products] | get all | Searching all existing wish list");
        return this.wishlistReportBusiness.getAll();
    }
}
