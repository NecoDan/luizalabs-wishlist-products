package br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseAccepted {

    @JsonProperty("processed")
    private String processed;

    @JsonIgnore
    public ResponseAccepted loadContentProcessed(boolean load) {
        this.processed = load ? "OK" : "NON_OK";
        return this;
    }
}
