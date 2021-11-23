package br.com.luizalabs.wishlist.products.presenter.rest.api.entities.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistRequest {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("itens")
    private List<ItemWishlistRequest> itemWishlist;
}
