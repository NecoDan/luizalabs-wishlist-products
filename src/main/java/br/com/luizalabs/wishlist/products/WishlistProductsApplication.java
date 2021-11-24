package br.com.luizalabs.wishlist.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@SpringBootApplication
//@ComponentScan(basePackages = {"br.com.luizalabs.wishlist.products.*/**/"})
public class WishlistProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishlistProductsApplication.class, args);
    }
}
