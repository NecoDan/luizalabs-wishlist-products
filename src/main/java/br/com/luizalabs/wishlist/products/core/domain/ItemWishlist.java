package br.com.luizalabs.wishlist.products.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemWishlist {

    private String id;
    private String wishlistId;
    private String productId;
    private String productName;
    private LocalDateTime dtCreated;
}
