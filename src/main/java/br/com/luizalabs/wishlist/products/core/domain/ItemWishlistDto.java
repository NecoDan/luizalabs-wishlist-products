package br.com.luizalabs.wishlist.products.core.domain;

import br.com.luizalabs.wishlist.products.model.IGenerateCreatedDate;
import br.com.luizalabs.wishlist.products.model.IGenerateIdentifier;
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
public class ItemWishlistDto implements IGenerateIdentifier, IGenerateCreatedDate {

    private String id;
    private String wishlistId;
    private String productId;
    private String productName;
    private LocalDateTime dtCreated;
}
