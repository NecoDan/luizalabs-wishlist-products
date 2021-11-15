package br.com.luizalabs.wishlist.products.dto.api;

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
public class ErrorResponseDto {
    private String message;
    private String details;
}
