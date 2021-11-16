package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateWishlistService implements IGenerateWishlistService {

    private final IWishlistService wishlistService;
    private final IValidateWishlist validateWishlist;

    @Override
    public Mono<Wishlist> create(Wishlist wishlist) {

        validateWishlist.validateParameters(wishlist);
        wishlist.generateId();
        wishlist.generateDtCreated();
        wishlist.popularItemsWishList();
        wishlist.loadTotalProductsItems();

        return saveWishList(wishlist);
    }

    @Override
    public Mono<Wishlist> removeProduct(String idWishlist, String idProduct) {

        validateWishlist.validateParametersRemoveProduct(idWishlist, idProduct);
        return wishlistService.getOneBy(idWishlist).flatMap(wishlist -> perfomProductRemoval(wishlist, idProduct));
    }

    @Override
    public Mono<Wishlist> perfomProductRemoval(Wishlist wishlist, String idProduct) {

        String strMsgDefault = " Trying to remove product in wish list. ";

        if (Objects.isNull(wishlist)) {
            validateWishlist.throwsValidationErrorAndLogError(strMsgDefault + "Wishlist is invalid and/or " +
                    "nonexistent (null).");
        }

        Optional<ItemWishlist> optional = wishlist.getItemWishlist()
                .stream()
                .filter(Objects::nonNull)
                .filter(i -> StringUtils.equalsIgnoreCase(i.getProductId(), idProduct))
                .findFirst();

        if (!optional.isPresent()) {
            validateWishlist.throwsValidationErrorAndLogError(String.format("Erro removing product in wish list. " +
                    "Product not found widh id: %s", idProduct));
        }

        return performRemoveProduct(wishlist, optional.get());
    }

    @Override
    public Mono<Wishlist> performRemoveProduct(Wishlist wishlist, ItemWishlist itemWishlist) {

        wishlist.removeOneItemWishList(itemWishlist);
        wishlist.loadTotalProductsItems();
        return saveWishList(wishlist);
    }

    private Mono<Wishlist> saveWishList(Wishlist wishlist) {
        return this.wishlistService.save(wishlist);
    }

}
