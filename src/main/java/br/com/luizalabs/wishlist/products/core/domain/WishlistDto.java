package br.com.luizalabs.wishlist.products.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {

    private String id;
    private String clientId;
    private String title;
    private LocalDateTime dtCreated;
    private int totalProducts;
    private List<ItemWishlistDto> itemWishlist;
}
