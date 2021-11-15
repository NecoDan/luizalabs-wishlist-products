package br.com.luizalabs.wishlist.products.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@Slf4j
@Document(collection = "item")
public class ItemWishlist {

    @Id
    private UUID id;

    @Field(name = "item")
    private Long item;

    @Field(name = "product_id")
    private UUID productId;

    @Field(name = "product_name")
    private String productName;

    @Field(name = "dt_created")
    private LocalDateTime dtCreated;

}
