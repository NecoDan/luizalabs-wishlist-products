package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistReportService implements IWishlistReportService {

    private final WishlistService wishlistService;

    @Override
    public Flux<Wishlist> getAll() {
        return this.wishlistService.findAll();
    }

    @Override
    public Mono<Wishlist> getOneBy(String id) {
        return this.wishlistService.getOneBy(id);
    }
}
