package br.com.luizalabs.wishlist.products.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Wishlist {

    private String id;
    private String clientId;
    private String title;
    private LocalDateTime dtCreated;
    private List<ItemWishlist> itemWishlist;
}
