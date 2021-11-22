package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.dto.wishlist.response.ProductItemWishlistDto;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface IWishlistReportService {

    Flux<Wishlist> getAll();

    Mono<Wishlist> getOneBy(String id);

    Mono<ProductItemWishlistDto> getProductByClientInWishlist(String idClient, String idProduct);

    Optional<ItemWishlist> getItemWishlistByProductsId(List<Wishlist> wishlists, String idProduct);
}
