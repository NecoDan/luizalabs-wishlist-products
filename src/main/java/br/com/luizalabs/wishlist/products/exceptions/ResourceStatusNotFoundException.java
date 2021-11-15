package br.com.luizalabs.wishlist.products.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
public class ResourceStatusNotFoundException extends HttpException {

    public ResourceStatusNotFoundException(String message) {
        super(String.format("Resource not found: %s.", message));
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
