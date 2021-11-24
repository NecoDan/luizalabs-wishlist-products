package br.com.luizalabs.wishlist.products.shared;

import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ItemWishlistResponse;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.modelmapper.ModelMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 15/1/2021
 */
public class WishlistCreator {

    public static ModelMapper createModelMapperForTests() {

        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Wishlist.class, WishlistResponse.class);
        modelMapper.createTypeMap(ItemWishlist.class, ItemWishlistResponse.class);
        modelMapper.createTypeMap(ItemWishlistRequest.class, ItemWishlist.class);
        modelMapper.createTypeMap(WishlistRequest.class, Wishlist.class);

        return modelMapper;
    }

    public static Wishlist createdValidWishListWithItems(String title, List<ItemWishlist> itemWishlists) {

        Wishlist wishlistSave = createdValidWishListNoItems(title).addAllItemWishlistThis(itemWishlists);
        wishlistSave.generateId();
        wishlistSave.generateDtCreated();
        wishlistSave.popularItemsWishList();
        wishlistSave.loadTotalProductsItems();

        return wishlistSave;
    }

    public static Wishlist createdValidWishListNoItems(String title) {

        return Wishlist.builder()
                .clientId(UUID.randomUUID().toString())
                .title(title)
                .build()
                .generateIdThis()
                .generateDtCreatedThis();
    }

    public static Wishlist createdValidWishListToBeSaved(String title) {

        var itemWishlists = Arrays.asList(WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                "Tenis Adidas Serie XXXX"),
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Tenis Nike Serie XIX"),
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Tenis Reebook Serie XIX"));

        var wishlist = createdValidWishListNoItems(title).addAllItemWishlistThis(itemWishlists);
        wishlist.popularItemsWishList();
        wishlist.loadTotalProductsItems();

        return wishlist;
    }

    public static ItemWishlist createdItemWishlist(UUID idProduct, String nameProduct) {

        return ItemWishlist.builder()
                .productId(idProduct.toString())
                .productName(nameProduct)
                .build()
                .generateIdThis()
                .generateDtCreatedThis();
    }

    public static String generateJsonValueFromObjectWishlist(Wishlist wishlist) throws JsonProcessingException {
        return inicializeObjectMapper().writeValueAsString(wishlist);
    }

    private static ObjectMapper inicializeObjectMapper() {

        var module = new JavaTimeModule();

        var localDateDeserializer = new LocalDateDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);

        var localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }
}
