package br.com.luizalabs.wishlist.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
public class WishlistProductsApplication {
    public static void main(String[] args) {
        SpringApplication.run(WishlistProductsApplication.class, args);
    }
}
