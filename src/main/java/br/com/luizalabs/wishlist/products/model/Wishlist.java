package br.com.luizalabs.wishlist.products.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
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
public class Wishlist implements IGenerateCreatedDate {

    @Id
    @Field("_id")
    private String id;

    @Field(name = "client_id")
    private String clientId;

    @Field(name = "title")
    private String title;

    @Field(name = "dt_created")
    private LocalDateTime dtCreated;

    @Field(name = "items")
    private List<ItemWishlist> itemWishlist;

    public void generateId() {
        this.id = IGenerateIdentifier.generateStringId();
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

    public void popularItemsWishList() {
        if (Objects.isNull(this.itemWishlist))
            return;
        this.itemWishlist.forEach(ItemWishlist::generateId);
        this.itemWishlist.forEach(ItemWishlist::generateDtCreatedThis);
    }

    private void inicializeItemWishlist() {
        if (Objects.isNull(this.itemWishlist)) {
            this.itemWishlist = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        var mapper = new ObjectMapper();
        var jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
