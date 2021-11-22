package br.com.luizalabs.wishlist.products.shared.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
public class ProductItemWishlistNotFoundException extends HttpException {

    public ProductItemWishlistNotFoundException(String message, String idProduct, String idClient) {
        super(String.format("%s Product referenced by id '%s' and Client referenced by id '%s' not found.",
                message, idProduct, idClient));
    }

    public ProductItemWishlistNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
