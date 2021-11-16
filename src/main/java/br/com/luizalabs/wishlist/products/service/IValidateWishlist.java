package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.exceptions.WishlistUnprocessableEntityException;
import br.com.luizalabs.wishlist.products.model.Wishlist;

public interface IValidateWishlist {

    void validadeInstanceWishlist(Wishlist wishlist);

    void validateParameters(Wishlist wishlist);

    WishlistUnprocessableEntityException getWishlistUnprocessableEntityException(String msg);

    void validateParametersRemoveProduct(String idWishlist, String idProduct);

    void throwsValidationErrorAndLogError(String msg);
}
