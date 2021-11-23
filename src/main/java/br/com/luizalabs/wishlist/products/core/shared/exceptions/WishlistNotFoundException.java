package br.com.luizalabs.wishlist.products.core.shared.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
public class WishlistNotFoundException extends HttpException {

    public WishlistNotFoundException(String message, String id) {
        super(String.format("Wish list %s '%s', not found.", message, id));
    }

    public WishlistNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
