package br.com.luizalabs.wishlist.products.model;

import java.time.LocalDateTime;

public interface IGenerateCreatedDate {

    static LocalDateTime generateCreatedDt() {
        return LocalDateTime.now();
    }
}
