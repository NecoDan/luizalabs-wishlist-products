package br.com.luizalabs.wishlist.products.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "items")
public class ItemWishlist implements IGenerateIdentifier, IGenerateCreatedDate {

    @Id
    @Field("_id")
    private String id;

    @Field(name = "product_id")
    private String productId;

    @Field(name = "product_name")
    private String productName;

    @Field(name = "dt_created")
    private LocalDateTime dtCreated;

    public void generateId() {
        this.id = IGenerateIdentifier.generateStringId();
    }

    public ItemWishlist generateIdThis() {
        generateId();
        return this;
    }

    public ItemWishlist generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }

    public void generateDtCreated() {
        this.dtCreated = IGenerateCreatedDate.generateCreatedDt();
    }
}
