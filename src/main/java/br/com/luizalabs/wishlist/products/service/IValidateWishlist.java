package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.dto.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistUnprocessableEntityException;
import br.com.luizalabs.wishlist.products.model.Wishlist;

public interface IValidateWishlist {

    void validadeInstanceWishlist(Wishlist wishlist);

    void validateParameters(Wishlist wishlist);

    void validateParametersAddProduct(String idWishlist, ItemWishlistRequest itemWishlistRequest);

    WishlistUnprocessableEntityException getWishlistUnprocessableEntityException(String msg);

    void validateParametersRemoveProduct(String idWishlist, String idProduct);

    void throwsValidationErrorAndLogError(String msg);
}
