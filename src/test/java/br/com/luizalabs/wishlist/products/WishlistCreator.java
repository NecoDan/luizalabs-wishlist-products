package br.com.luizalabs.wishlist.products;


import br.com.luizalabs.wishlist.products.dto.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.ItemWishlistDto;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
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

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Wishlist.class, WishlistDto.class);
        modelMapper.createTypeMap(ItemWishlist.class, ItemWishlistDto.class);
        modelMapper.createTypeMap(ItemWishlistRequest.class, ItemWishlist.class);
        modelMapper.createTypeMap(WishlistRequest.class, Wishlist.class);

        return modelMapper;
    }

    public static Wishlist createdValidWishListWithItems(String title, List<ItemWishlist> itemWishlists) {
        return createdValidWishListNoItems(title).addAllItemWishlistThis(itemWishlists);
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
