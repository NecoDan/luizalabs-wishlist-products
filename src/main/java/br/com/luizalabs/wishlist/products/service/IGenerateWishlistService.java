package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.Wishlist;
import reactor.core.publisher.Mono;

public interface IGenerateWishlistService {

    Mono<Wishlist> create(Wishlist wishlist);

}
