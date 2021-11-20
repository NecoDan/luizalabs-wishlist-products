package br.com.luizalabs.wishlist.products.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Daniel Santos
 * @since 19/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProductItemWishlist {

    @Field("_id")
    private String id;
}
