package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IWishlistReportService {

    Flux<Wishlist> getAll();

    Mono<Wishlist> getOneBy(String id);

}
