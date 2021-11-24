package br.com.luizalabs.wishlist.products.core.usercase;

import br.com.luizalabs.wishlist.products.core.port.GenerateWishlistPort;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class PostOneWishlistUseCaseImpl implements PostOneWishlistUseCase {

    private final GenerateWishlistPort generateWishlistService;

    @Override
    public Mono<Wishlist> execute(Wishlist wishlist) {

        log.info("[luizalabs-wishlist-products] | postOneWishlistUseCaseImpl() | Call to create wishtlist: {}.", wishlist);
        return this.generateWishlistService.create(wishlist);
    }
}
