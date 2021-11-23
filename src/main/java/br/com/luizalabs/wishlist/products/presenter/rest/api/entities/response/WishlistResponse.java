package br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
@ToString
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
