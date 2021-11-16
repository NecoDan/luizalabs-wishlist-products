package br.com.luizalabs.wishlist.products.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
public class ItemWishlistNotFoundException extends HttpException {

    public ItemWishlistNotFoundException(String message) {
        super(String.format("Item product wish list '%s' not found.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
