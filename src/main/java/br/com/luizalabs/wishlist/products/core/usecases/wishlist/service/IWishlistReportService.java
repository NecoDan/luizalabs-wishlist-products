package br.com.luizalabs.wishlist.products.core.usecases.wishlist.service;

import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.ProductItemWishlistResponse;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface IWishlistReportService {

    Flux<Wishlist> getAll();

    Mono<Wishlist> getOneBy(String id);

    Mono<ProductItemWishlistResponse> getProductByClientInWishlist(String idClient, String idProduct);

    Optional<ItemWishlist> getItemWishlistByProductsId(List<Wishlist> wishlists, String idProduct);
}
