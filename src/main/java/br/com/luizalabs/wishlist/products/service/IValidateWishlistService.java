package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.dto.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistUnProcessableEntityException;
import br.com.luizalabs.wishlist.products.model.Wishlist;

public interface IValidateWishlistService {

    void validadeInstanceWishlist(Wishlist wishlist);

    void validateParameters(Wishlist wishlist);

    void validateParametersAddProduct(String idWishlist, ItemWishlistRequest itemWishlistRequest);

    WishlistUnProcessableEntityException getWishlistUnprocessableEntityException(String msg);

    void validateParametersRemoveProduct(String idWishlist, String idProduct);

    void throwsValidationErrorAndLogError(String msg);
}
