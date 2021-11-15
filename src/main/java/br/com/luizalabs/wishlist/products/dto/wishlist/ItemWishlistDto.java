package br.com.luizalabs.wishlist.products.dto.wishlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ItemWishlistDto {

    private UUID id;

    private Long item;

    @JsonProperty("product_id")
    private UUID productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("dt_created")
    private LocalDateTime dtCreated;
}
