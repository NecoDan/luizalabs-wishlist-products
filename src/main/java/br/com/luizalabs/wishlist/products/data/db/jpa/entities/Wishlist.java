package br.com.luizalabs.wishlist.products.data.db.jpa.entities;

import br.com.luizalabs.wishlist.products.model.IGenerateCreatedDate;
import br.com.luizalabs.wishlist.products.model.IGenerateIdentifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wishlist")
public class Wishlist implements IGenerateCreatedDate, IGenerateIdentifier {

    @Id
    @NonFinal
    private String id;

    @Field(name = "client_id")
    private String clientId;

    @Field(name = "title")
    private String title;

    @Field(name = "dt_created")
    private LocalDateTime dtCreated;

    @Field(name = "total_products")
    private int totalProducts;

    @Field(name = "itens_products")
    private List<ItemWishlist> itemWishlist;

    public void generateId() {
        this.id = IGenerateIdentifier.generateStringId();
    }

    public Wishlist generateIdThis() {
        generateId();
        return this;
    }

    public void generateDtCreated() {
        this.dtCreated = IGenerateCreatedDate.generateCreatedDt();
    }

    public Wishlist generateDtCreatedThis() {
        generateDtCreated();
        return this;
    }

    public void addItemWishlist(ItemWishlist itemWishlist) {
        inicializeItemWishlist();
        this.itemWishlist.add(itemWishlist);
    }

    public Wishlist addItemWishlistThis(ItemWishlist itemWishlist) {
        addItemWishlist(itemWishlist);
        return this;
    }

    public void addAllItemWishlist(List<ItemWishlist> itemWishlists) {
        this.itemWishlist.addAll(itemWishlists);
    }

    public Wishlist addAllItemWishlistThis(List<ItemWishlist> itemWishlists) {
        inicializeItemWishlist();
        addAllItemWishlist(itemWishlists);
        return this;
    }

    public void removeOneItemWishList(ItemWishlist itemWishlist) {
        if (Objects.isNull(this.itemWishlist))
            return;
        this.itemWishlist.remove(itemWishlist);
    }

    public void popularItemsWishList() {
        if (Objects.isNull(this.itemWishlist))
            return;
        this.itemWishlist.forEach(i -> i.fillItem(this.id));
    }

    private void inicializeItemWishlist() {
        if (Objects.isNull(this.itemWishlist)) {
            this.itemWishlist = new ArrayList<>();
        }
    }

    public void loadTotalProductsItems() {
        inicializeItemWishlist();
        this.totalProducts = this.itemWishlist.size();
    }

    public boolean isTotalProductsAddedToInvalidWishlist(long maxLimitProducts) {
        return (Objects.nonNull(this.itemWishlist) && this.itemWishlist.size() > maxLimitProducts);
    }

    @Override
    public String toString() {
        var mapper = new ObjectMapper();
        var result = "";

        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            result = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
