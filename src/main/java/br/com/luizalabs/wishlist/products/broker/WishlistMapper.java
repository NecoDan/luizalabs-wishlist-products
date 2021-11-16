package br.com.luizalabs.wishlist.products.broker;


import br.com.luizalabs.wishlist.products.config.ModelMapperConfig;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WishlistMapper {

    private final ModelMapperConfig modelMapperConfig;

    public WishlistDto toWishlistDtoFrom(Wishlist wishlist) {
        log.info("[luizalabs-wishlist-products] | Created wishlist response: {}", wishlist);
        return modelMapperConfig.modelMapper().map(wishlist, WishlistDto.class);
    }

    public Wishlist toWishlistFrom(WishlistDto wishlistDto) {
        log.info("[luizalabs-wishlist-products] | Created wishlist: {}", wishlistDto);
        return modelMapperConfig.modelMapper().map(wishlistDto, Wishlist.class);
    }

    public Wishlist toWishlistFromRequest(WishlistRequest wishlistRequest) {
        log.info("[luizalabs-wishlist-products] | Created wishlist request: {}", wishlistRequest);
        return modelMapperConfig.modelMapper().map(wishlistRequest, Wishlist.class);
    }
}
