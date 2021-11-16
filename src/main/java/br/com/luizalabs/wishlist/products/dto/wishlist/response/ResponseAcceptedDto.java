package br.com.luizalabs.wishlist.products.dto.wishlist.response;

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
public class ResponseAcceptedDto {

    @JsonProperty("processed")
    private String processed;

    @JsonIgnore
    public ResponseAcceptedDto loadContentProcessed(boolean load) {
        this.processed = load ? "OK" : "NON_OK";
        return this;
    }
}
