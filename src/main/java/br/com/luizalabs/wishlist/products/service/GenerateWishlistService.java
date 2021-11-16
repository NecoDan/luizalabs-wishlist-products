package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.exceptions.WishlistUnprocessableEntityException;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.properties.TransactionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateWishlistService implements IGenerateWishlistService {

    private final IWishlistService wishlistService;
    private final TransactionProperties transactionProperties;

    @Override
    public Mono<Wishlist> create(Wishlist wishlist) {

        validateParams(wishlist);
        wishlist.generateId();
        wishlist.generateDtCreated();
        wishlist.popularItemsWishList();

        return this.wishlistService.save(wishlist);
    }

    public void validateParams(Wishlist wishlist) {
        String strMsgDefault = " Trying to create wish list. ";

        if (Objects.isNull(wishlist)) {
            throwsValidationErrorAndLogError(strMsgDefault + "Wishlist is invalid and/or nonexistent (null).");
        }

        if (Objects.isNull(wishlist.getClientId())) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list cliend id is invalid and / or not informed (null).");
        }

        if (StringUtils.isEmpty(wishlist.getTitle())) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list title is invalid and / or not informed.");
        }

        if (isTotalProductsAddedToInvalidWishlist(wishlist)) {
            throwsValidationErrorAndLogError(strMsgDefault + "Total products added to wish list must be less than or equal to "
                    + transactionProperties.getLimitMaxProductsWishlist());
        }
    }

    private boolean isTotalProductsAddedToInvalidWishlist(Wishlist wishlist) {
        return (Objects.nonNull(wishlist.getItemWishlist())
                && wishlist.getItemWishlist().size() > transactionProperties.getLimitMaxProductsWishlist());
    }

    private void throwsValidationErrorAndLogError(String msg) {
        log.error("[luizalabs-wishlist-products] | GenerateWishlistService | Error: {}", msg);
        throw new WishlistUnprocessableEntityException(msg);
    }
}
