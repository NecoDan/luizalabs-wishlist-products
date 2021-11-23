package br.com.luizalabs.wishlist.products.core.usecases.wishlist.service;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.ProductItemWishlistResponse;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import br.com.luizalabs.wishlist.products.core.shared.exceptions.ProductItemWishlistNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @Override
    public Flux<Wishlist> getAll() {
        return this.wishlistService.findAll();
    }

    @Override
    public Mono<Wishlist> getOneBy(String id) {
        return this.wishlistService.findById(id);
    }

    @Override
    public Mono<ProductItemWishlistResponse> getProductByClientInWishlist(String idClient, String idProduct) {

        List<Wishlist> wishlists = this.wishlistService
                .findAllWishListClientIdAndProductId(idClient, idProduct);

        if (wishlists.isEmpty()) {
            throw getProductItemWishlistNotFoundException(idProduct, idClient);
        }

        var productItemWishlist = getItemWishlistByProductsId(wishlists, idProduct)
                .map(this::getProductItemWishlistDto)
                .orElseThrow(() -> getProductItemWishlistNotFoundException(idProduct, idClient));

        return Mono.just(productItemWishlist)
                .switchIfEmpty(Mono.defer(()
                        -> Mono.error(getProductItemWishlistNotFoundException(idProduct, idClient))));
    }

    @Override
    public Optional<ItemWishlist> getItemWishlistByProductsId(List<Wishlist> wishlists, String idProduct) {

        List<ItemWishlist> itemWishlistsAll = new ArrayList<>();
        wishlists.forEach(wishlist -> itemWishlistsAll.addAll(wishlist.getItemWishlist()));

        return itemWishlistsAll.stream()
                .filter(Objects::nonNull)
                .filter(i -> StringUtils.equalsIgnoreCase(i.getProductId(), idProduct))
                .collect(Collectors.groupingBy(ItemWishlist::getProductId))
                .get(idProduct)
                .stream()
                .filter(Objects::nonNull)
                .filter(i -> StringUtils.equalsIgnoreCase(i.getProductId(), idProduct))
                .findFirst();
    }

    private ProductItemWishlistResponse getProductItemWishlistDto(ItemWishlist itemWishlist) {
        return convertToProductItemWishlistDto(itemWishlist);
    }

    private ProductItemWishlistResponse convertToProductItemWishlistDto(ItemWishlist itemWishlist) {
        return wishlistMapper.toProductItemWishlistDtoFrom(itemWishlist);
    }

    private ProductItemWishlistNotFoundException getProductItemWishlistNotFoundException(String idProduct,
                                                                                         String idClient) {
        return new ProductItemWishlistNotFoundException("", idProduct, idClient);
    }
}
