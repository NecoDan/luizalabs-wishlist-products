package br.com.luizalabs.wishlist.products.dto.wishlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistDto {

    private UUID id;

    @JsonProperty("client_id")
    private UUID clientId;

    private String title;

    @JsonProperty("dt_created")
    private LocalDateTime dtCreated;

    @JsonProperty("itens")
    private List<ItemWishlistDto> itemWishlistDtoList;
}
