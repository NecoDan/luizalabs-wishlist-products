package br.com.luizalabs.wishlist.products.shared.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
public class WishlistUnProcessableEntityException extends HttpException {

    public WishlistUnProcessableEntityException(String message) {
        super(String.format("Could not process the wishlist: %s.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
