package br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response;

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
public class ResponseAcceptedResponse {

    @JsonProperty("processed")
    private String processed;

    @JsonIgnore
    public ResponseAcceptedResponse loadContentProcessed(boolean load) {
        this.processed = load ? "OK" : "NON_OK";
        return this;
    }
}
