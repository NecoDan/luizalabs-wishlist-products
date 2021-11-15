package br.com.luizalabs.wishlist.products.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
@Document(collection = "wishlist")
public class Wishlist implements IGenerateCreatedDate {

    @Id
    private UUID id;

    @Field(name = "client_id")
    private UUID clientId;

    @Field(name = "title")
    private String title;

    @Field(name = "dt_created")
    private LocalDateTime dtCreated;

    @Field(name = "itens")
    private List<ItemWishlist> itemWishlist;

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
