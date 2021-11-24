package br.com.luizalabs.wishlist.products.data.entities;

import br.com.luizalabs.wishlist.products.shared.util.IGenerateCreatedDate;
import br.com.luizalabs.wishlist.products.shared.util.IGenerateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;
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
@Document(collection = "item_wishlist")
public class ItemWishlist implements IGenerateIdentifier, IGenerateCreatedDate {

    @Id
    @NonFinal
    private String id;

    @Field(name = "wishlist_id")
    private String wishlistId;

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

    public void fillWishlistId(String idWishlist) {
        this.wishlistId = idWishlist;
    }

    public ItemWishlist fillWishlistIdThis(String idWishlist) {
        fillWishlistId(idWishlist);
        return this;
    }

    public ItemWishlist generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }

    public void generateDtCreated() {
        this.dtCreated = IGenerateCreatedDate.generateCreatedDt();
    }

    public void fillItem(String idWishlist) {
        generateId();
        fillWishlistId(idWishlist);
        generateDtCreatedThis();
    }

}
