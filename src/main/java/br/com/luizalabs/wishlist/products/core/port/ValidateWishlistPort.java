package br.com.luizalabs.wishlist.products.core.port;

import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistUnProcessableEntityException;
import org.springframework.stereotype.Service;

@Service
public interface ValidateWishlistPort {

    void validadeInstanceWishlist(Wishlist wishlist);

    void validateParameters(Wishlist wishlist);

    void validateParametersAddProduct(String idWishlist, ItemWishlistRequest itemWishlistRequest);

    WishlistUnProcessableEntityException getWishlistUnprocessableEntityException(String msg);

    void validateParametersRemoveProduct(String idWishlist, String idProduct);

    void throwsValidationErrorAndLogError(String msg);
}
