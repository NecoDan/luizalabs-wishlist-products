package br.com.luizalabs.wishlist.products.dto.wishlist.request;

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
public class ProductRemoveRequest {

    @JsonProperty("product_id")
    private String idProduct;
}
