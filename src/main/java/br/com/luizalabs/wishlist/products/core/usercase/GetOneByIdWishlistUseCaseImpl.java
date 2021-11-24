package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.core.biz.WishlistReportBusiness;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class GetOneByIdWishlistUseCaseImpl implements GetOneByIdWishlistUseCase {

    private final WishlistReportBusiness wishlistReportBusiness;

    @Override
    public Mono<Wishlist> execute(String id) {

        log.info("[luizalabs-wishlist-products] | get one by id | Searching for an existing Wish list by id");
        return this.wishlistReportBusiness.getOneBy(id);
    }
}
