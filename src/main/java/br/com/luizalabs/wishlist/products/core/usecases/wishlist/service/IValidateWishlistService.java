package br.com.luizalabs.wishlist.products.core.usecases.wishlist.service;

import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.core.shared.exceptions.WishlistUnProcessableEntityException;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;

public interface IValidateWishlistService {

    void validadeInstanceWishlist(Wishlist wishlist);

    void validateParameters(Wishlist wishlist);

    void validateParametersAddProduct(String idWishlist, ItemWishlistRequest itemWishlistRequest);

    WishlistUnProcessableEntityException getWishlistUnprocessableEntityException(String msg);

    void validateParametersRemoveProduct(String idWishlist, String idProduct);

    void throwsValidationErrorAndLogError(String msg);
}
