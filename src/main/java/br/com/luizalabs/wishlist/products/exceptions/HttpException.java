package br.com.luizalabs.wishlist.products.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
public abstract class HttpException extends RuntimeException {

    HttpException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
