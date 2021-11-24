package br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("dt_created")
    private LocalDateTime dtCreated;

    @JsonProperty("itens_product")
    private List<ItemWishlistResponse> itemWishlist;
}
