package br.com.luizalabs.wishlist.products.delivery.entities.api;

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
public class ApiErrorResponse {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("message_log")
    private String messageLog;

    @JsonProperty("details")
    private String details;

    @JsonProperty("errors")
    List<String> errors;

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("path")
    private String path;
}
