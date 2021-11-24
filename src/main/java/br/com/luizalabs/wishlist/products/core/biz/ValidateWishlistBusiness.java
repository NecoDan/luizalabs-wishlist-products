package br.com.luizalabs.wishlist.products.core.biz;

import br.com.luizalabs.wishlist.products.core.port.ValidateWishlistPort;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistUnProcessableEntityException;
import br.com.luizalabs.wishlist.products.shared.properties.ParamLimitProductProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateWishlistBusiness implements ValidateWishlistPort {

    private final ParamLimitProductProperties properties;

    @Override
    public void validadeInstanceWishlist(Wishlist wishlist) {
        if (Objects.isNull(wishlist)) {
            throwsValidationErrorAndLogError(" Trying to create wish list. Wishlist is invalid and/or nonexistent (null).");
        }
    }

    @Override
    public void validateParameters(Wishlist wishlist) {
        var strMsgDefault = " Trying to create wish list. ";
        validadeInstanceWishlist(wishlist);

        if (Objects.isNull(wishlist.getClientId())) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list cliend id is invalid and / or not informed (null).");
        }

        if (StringUtils.isEmpty(wishlist.getTitle())) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list title is invalid and / or not informed.");
        }

        long limitMaxProductsAdd = properties.getLimitMaxProductsWishlist();
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
    public void validateParametersAddProduct(String idWishlist, ItemWishlistRequest itemWishlistRequest) {
        var strMsgDefault = " Trying to remove product in wish list. ";

        if (Objects.isNull(idWishlist)) {
            throwsValidationErrorAndLogError(strMsgDefault + "Wishlist is invalid and/or nonexistent (null).");
        }

        if (Objects.isNull(itemWishlistRequest)) {
            throwsValidationErrorAndLogError(strMsgDefault + "Parameter to add a new product to the wish list, is invalid and / or not informed (null).");
        }

        if (Objects.nonNull(itemWishlistRequest) && StringUtils.isEmpty(itemWishlistRequest.getProductId())) {
            throwsValidationErrorAndLogError(strMsgDefault + "The wish list product id to be removed, is invalid and / or not informed (null).");
        }
    }

    @Override
    public WishlistUnProcessableEntityException getWishlistUnprocessableEntityException(String msg) {
        log.error("[luizalabs-wishlist-products] | GenerateWishlistService | Error: {}", msg);
        return new WishlistUnProcessableEntityException(msg);
    }

    @Override
    public void throwsValidationErrorAndLogError(String msg) {
        throw getWishlistUnprocessableEntityException(msg);
    }
}
