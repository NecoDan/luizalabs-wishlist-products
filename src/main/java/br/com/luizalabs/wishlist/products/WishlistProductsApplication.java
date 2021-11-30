package br.com.luizalabs.wishlist.products;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API RestFULL - LuizaLabs MAGALU | Wish list.",
        version = "1.0.0",
        description = "Documentation APIs v1.0.0 | Endpoint access with Swagger - [MVP API WISH LIST]",
        contact = @Contact(name = "Daniel Santos", url = "https://github.com/NecoDan", email = "neco.daniel@gmail.com"),
        license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
))
public class WishlistProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WishlistProductsApplication.class, args);
    }
}
