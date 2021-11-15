package br.com.luizalabs.wishlist.products;


import br.com.luizalabs.wishlist.products.dto.wishlist.ItemWishlistDto;
import br.com.luizalabs.wishlist.products.dto.wishlist.WishlistDto;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 15/1/2021
 */
public class WishlistCreator {

    public static ModelMapper createModelMapperForTests() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Wishlist.class, WishlistDto.class);
        modelMapper.createTypeMap(ItemWishlist.class, ItemWishlistDto.class);
        return modelMapper;
    }

    public static Wishlist createdValidWishListWithItems(String title, List<ItemWishlist> itemWishlists) {
        return createdValidWishListNoItems(title).addAllItemWishlistThis(itemWishlists);
    }

    public static Wishlist createdValidWishListNoItems(String title) {

        return Wishlist.builder()
                .id(UUID.randomUUID())
                .clientId(UUID.randomUUID())
                .title(title)
                .build()
                .generateDtCreatedThis();
    }

    public static Wishlist createdValidWishListToBeSaved(String title) {
        List<ItemWishlist> itemWishlists = Arrays.asList(WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                "Tenis Adidas Serie XXXX"),
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Tenis Nike Serie XIX"),
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Tenis Reebook Serie XIX"));

        return createdValidWishListNoItems(title).addAllItemWishlistThis(itemWishlists);
    }

    public static ItemWishlist createdItemWishlist(UUID idProduct, String nameProduct) {

        return ItemWishlist.builder()
                .id(UUID.randomUUID())
                .productId(idProduct)
                .productName(nameProduct)
                .build()
                .generateDtCreatedThis();
    }

    private static ObjectMapper inicializeObjectMapper() {

        JavaTimeModule module = new JavaTimeModule();

        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);

        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        return Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }
}
