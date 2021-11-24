package br.com.luizalabs.wishlist.products.shared;


import br.com.luizalabs.wishlist.products.delivery.configuration.ModelMapperConfig;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ProductRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ProductItemWishlistResponse;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
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

    public WishlistResponse toWishlistResponseFrom(Wishlist entity) {
        return this.modelMapperConfig.modelMapper().map(entity, WishlistResponse.class);
    }

    public Wishlist toWishlistFrom(WishlistResponse entity) {
        return this.modelMapperConfig.modelMapper().map(entity, Wishlist.class);
    }

    public Wishlist toWishlistFromRequest(WishlistRequest entity) {
        return this.modelMapperConfig.modelMapper().map(entity, Wishlist.class);
    }

    public ItemWishlistRequest toItemWishlistRequestFrom(ProductRequest entity) {
        return this.modelMapperConfig.modelMapper().map(entity, ItemWishlistRequest.class);
    }

    public ItemWishlist toItemWishlistFrom(ItemWishlistRequest entity) {
        return this.modelMapperConfig.modelMapper().map(entity, ItemWishlist.class);
    }

    public ProductItemWishlistResponse toProductItemWishlistResponseFrom(ItemWishlist entity) {
        return this.modelMapperConfig.modelMapper().map(entity, ProductItemWishlistResponse.class);
    }

}
