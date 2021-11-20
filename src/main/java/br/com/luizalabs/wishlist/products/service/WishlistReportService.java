package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.ProductItemWishlistDto;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.ProductItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.repository.WishlistRepository;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistReportService implements IWishlistReportService {

    private final WishlistService wishlistService;
    private final WishlistMapper wishlistMapper;
    private final WishlistRepository wishlistRepository;

    @Override
    public Flux<Wishlist> getAll() {
        return this.wishlistService.findAll();
    }

    @Override
    public Mono<Wishlist> getOneBy(String id) {
        return this.wishlistService.findById(id);
    }

    @Override
    public Mono<ProductItemWishlist> getProductByClientInWishlist(String idClient, String idProduct) {

//        Mono<String> result = wishlistRepository.getWishListClientIdProductIdFrom(idClient, idProduct);

        //Flux<Wishlist> wishlistFlux = wishlistRepository.getWishListClientIdProductIdFrom(idClient, idProduct);
//
        Flux<Wishlist> wishlistFlux = wishlistRepository.findAllByClientId(idClient)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistNotFoundException(idClient))));
//
        return wishlistFlux.map(this::getItemWishlistByProductsId)
                .flatMap(itemWishlists -> outFrom(itemWishlists))
                .single();

//        Mono<Wishlist> productItemWishlistMono = wishlistRepository
//                .getWishListClientIdProductIdFrom(idClient, idProduct)
//                .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistNotFoundException(idClient))));

//        return productItemWishlistMono.flatMap(wishlist -> out(wishlist));
//        log.info("Result: {}", result);
//
//        stringMono.doOnEach(s -> log.info("{}", s));
//        Flux<Object> wishlistFlux = Flux.fromIterable(Collections.emptyList())
//            .switchIfEmpty(Mono.defer(() -> Mono.error(new WishlistNotFoundException(idClient))));

//        return Flux.fromIterable(List.of(ProductItemWishlist.builder().build()));
    }

    public Mono<ProductItemWishlist> out(Wishlist str) {

        System.out.println("Chegou aqui....");
        log.info("{}", str);

        return Mono.just(ProductItemWishlist.builder().build());
    }

    public Mono<ProductItemWishlist> outFrom(List<ItemWishlist> itemWishlists) {

        System.out.println("Chegou aqui....");
        log.info("{}", itemWishlists);

        return Mono.just(ProductItemWishlist.builder().build());
    }

    private List<ItemWishlist> getItemWishlistByProductsId(Wishlist wishlist) {

        System.out.println("Chegou aqui....");
        log.info("{}", wishlist);

        return wishlist.getItemWishlist()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ProductItemWishlistDto getProductItemWishlistDto(ItemWishlist itemWishlist) {
        return convertToProductItemWishlistDto(itemWishlist);
    }

    private ProductItemWishlistDto convertToProductItemWishlistDto(ItemWishlist itemWishlist) {
        return wishlistMapper.toProductItemWishlistDtoFrom(itemWishlist);
    }
}
