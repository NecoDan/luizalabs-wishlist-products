package br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemWishlistResponse {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_name")
    private String productName;
}
