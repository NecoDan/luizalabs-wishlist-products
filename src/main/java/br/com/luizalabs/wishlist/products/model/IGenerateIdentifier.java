package br.com.luizalabs.wishlist.products.model;

import java.util.UUID;

public interface IGenerateIdentifier {

    static String generateStringId() {
        return generateId().toString();
    }

    static UUID generateId() {
        return UUID.randomUUID();
    }
}
