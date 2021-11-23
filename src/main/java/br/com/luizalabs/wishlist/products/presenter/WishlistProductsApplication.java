package br.com.luizalabs.wishlist.products.presenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@SpringBootApplication
public class WishlistProductsApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(WishlistProductsApplication.class, args);
    }
}
