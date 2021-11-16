package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.exceptions.WishlistUnprocessableEntityException;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.properties.TransactionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateWishlist implements IValidateWishlist {

    private final TransactionProperties transactionProperties;

    @Override
    public void validadeInstanceWishlist(Wishlist wishlist) {
        if (Objects.isNull(wishlist)) {
            throwsValidationErrorAndLogError(" Trying to create wish list. Wishlist is invalid and/or nonexistent (null).");
        }
    }

    @Override
    public void validateParameters(Wishlist wishlist) {
        String strMsgDefault = " Trying to create wish list. ";
        validadeInstanceWishlist(wishlist);

        if (Objects.isNull(wishlist.getClientId())) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list cliend id is invalid and / or not informed (null).");
        }

        if (StringUtils.isEmpty(wishlist.getTitle())) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list title is invalid and / or not informed.");
        }

        long limitMaxProductsAdd = transactionProperties.getLimitMaxProductsWishlist();
        if (wishlist.isTotalProductsAddedToInvalidWishlist(limitMaxProductsAdd)) {
            throwsValidationErrorAndLogError(strMsgDefault + "Total products added to wish list must be less than or equal to "
                    + limitMaxProductsAdd);
        }
    }

    @Override
    public void validateParametersRemoveProduct(String idWishlist, String idProduct) {
        String strMsgDefault = " Trying to remove product in wish list. ";

        if (Objects.isNull(idWishlist)) {
            throwsValidationErrorAndLogError(strMsgDefault + "Wishlist is invalid and/or nonexistent (null).");
        }

        if (StringUtils.isEmpty(idProduct)) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list product id to be removed, is invalid and / or not informed (null).");
        }
    }

    @Override
    public WishlistUnprocessableEntityException getWishlistUnprocessableEntityException(String msg) {
        log.error("[luizalabs-wishlist-products] | GenerateWishlistService | Error: {}", msg);
        return new WishlistUnprocessableEntityException(msg);
    }

    @Override
    public void throwsValidationErrorAndLogError(String msg) {
        throw getWishlistUnprocessableEntityException(msg);
    }
}
