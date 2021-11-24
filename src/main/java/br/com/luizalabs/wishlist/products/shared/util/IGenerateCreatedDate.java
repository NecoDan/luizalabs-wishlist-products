package br.com.luizalabs.wishlist.products.shared.util;

import java.time.LocalDateTime;

public interface IGenerateCreatedDate {

    static LocalDateTime generateCreatedDt() {
        return LocalDateTime.now();
    }
}
