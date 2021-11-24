package br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemWishlistResponse {

    @JsonProperty("item_id")
    private String id;

    @JsonProperty("wishlist_id")
    private String wishlistId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("dt_created")
    private LocalDateTime dtCreated;
}
