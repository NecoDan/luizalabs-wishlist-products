package br.com.luizalabs.wishlist.products.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "transaction")
public class TransactionProperties {
    private long limitMaxProductsWishlist;
}
