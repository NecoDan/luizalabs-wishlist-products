package br.com.luizalabs.wishlist.products.core.biz;

import br.com.luizalabs.wishlist.products.core.port.GenerateWishlistPort;
import br.com.luizalabs.wishlist.products.core.port.ValidateWishlistPort;
import br.com.luizalabs.wishlist.products.core.port.WishlistPort;
import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.shared.WishlistMapper;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GenerateWishlistBusiness implements GenerateWishlistPort {

    private final WishlistPort wishlistService;

    private final ValidateWishlistPort validateWishlistService;

    private final WishlistMapper wishlistMapper;

    @Override
    public Mono<Wishlist> create(Wishlist wishlist) {

        validateWishlistService.validateParameters(wishlist);
        wishlist.generateId();
        wishlist.generateDtCreated();
        wishlist.popularItemsWishList();
        wishlist.loadTotalProductsItems();

        return perfomSaveWishlist(wishlist);
    }

    @Override
    public Mono<Wishlist> perfomSaveWishlist(Wishlist wishlist) {
        return saveWishList(wishlist);
    }

    @Override
    public Mono<Wishlist> addProduct(String idWishlist, ItemWishlistRequest itemWishlistRequest) {

        validateWishlistService.validateParametersAddProduct(idWishlist, itemWishlistRequest);
        return wishlistService.findById(idWishlist)
                .flatMap(wishlist -> perfomProductAdd(wishlist, itemWishlistRequest));
    }

    private boolean checkProductAaddedWishlist(String idProduct, Wishlist wishlist) {

        return wishlist.getItemWishlist().stream()
                .anyMatch(i -> StringUtils.equalsIgnoreCase(idProduct, i.getProductId()));
    }

    public Mono<Wishlist> finalizePerfomProductAdd(Wishlist wishlist, ItemWishlistRequest itemWishlistRequest) {

        var itemWishlist = wishlistMapper.toItemWishlistFrom(itemWishlistRequest);
        itemWishlist.fillItem(wishlist.getId());
        wishlist.addItemWishlist(itemWishlist);
        wishlist.loadTotalProductsItems();

        return saveWishList(wishlist);
    }

    @Override
    public Mono<Wishlist> perfomProductAdd(Wishlist wishlist, ItemWishlistRequest itemWishlistRequest) {

        return (checkProductAaddedWishlist(itemWishlistRequest.getProductId(), wishlist))
                ? Mono.just(wishlist)
                : finalizePerfomProductAdd(wishlist, itemWishlistRequest);
    }

    @Override
    public Mono<Wishlist> removeProduct(String idWishlist, String idProduct) {

        validateWishlistService.validateParametersRemoveProduct(idWishlist, idProduct);
        return wishlistService.findById(idWishlist)
                .flatMap(wishlist -> perfomProductRemoval(wishlist, idProduct));
    }

    @Override
    public Mono<Wishlist> perfomProductRemoval(Wishlist wishlist, String idProduct) {

        performValidateWishList(wishlist);
        Optional<ItemWishlist> optional = wishlist.getItemWishlist()
                .stream()
                .filter(Objects::nonNull)
                .filter(i -> StringUtils.equalsIgnoreCase(i.getProductId(), idProduct))
                .findFirst();

        ItemWishlist itemWishlist = optional.orElseThrow(() -> validateWishlistService
                .getWishlistUnprocessableEntityException(String.format("Erro removing product in wish list. " +
                        "Product not found widh id: %s", idProduct)));

        return performRemoveProduct(wishlist, itemWishlist);
    }

    @Override
    public Mono<Wishlist> performRemoveProduct(Wishlist wishlist, ItemWishlist itemWishlist) {

        wishlist.removeOneItemWishList(itemWishlist);
        wishlist.loadTotalProductsItems();
        return saveWishList(wishlist);
    }

    private void performValidateWishList(Wishlist wishlist) {

        var strMsgDefault = " Trying to remove product in wish list. ";

        if (Objects.isNull(wishlist)) {
            validateWishlistService.throwsValidationErrorAndLogError(strMsgDefault + "Wishlist is invalid and/or " +
                    "nonexistent (null).");
        }
    }

    private Mono<Wishlist> saveWishList(Wishlist wishlist) {
        return this.wishlistService.save(wishlist);
    }
}
