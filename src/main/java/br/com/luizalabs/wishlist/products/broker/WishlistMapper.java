package br.com.luizalabs.wishlist.products.broker;


import br.com.luizalabs.wishlist.products.config.ModelMapperConfig;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.ProductRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@Component
@RequiredArgsConstructor
public class WishlistMapper {

    private final ModelMapperConfig modelMapperConfig;

    public WishlistDto toWishlistDtoFrom(Wishlist entity) {
        return this.modelMapperConfig.modelMapper().map(entity, WishlistDto.class);
    }

    public Wishlist toWishlistFrom(WishlistDto entity) {
        return this.modelMapperConfig.modelMapper().map(entity, Wishlist.class);
    }

    public Wishlist toWishlistFromRequest(WishlistRequest entity) {
        return this.modelMapperConfig.modelMapper().map(entity, Wishlist.class);
    }

    public ItemWishlistRequest toWishlistRequestFrom(ProductRequest entity) {
        return this.modelMapperConfig.modelMapper().map(entity, ItemWishlistRequest.class);
    }

    public ItemWishlist toItemWishlistFrom(ItemWishlistRequest entity) {
        return this.modelMapperConfig.modelMapper().map(entity, ItemWishlist.class);
    }
}
