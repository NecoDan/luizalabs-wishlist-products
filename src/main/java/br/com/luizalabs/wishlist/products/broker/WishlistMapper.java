package br.com.luizalabs.wishlist.products.broker;


import br.com.luizalabs.wishlist.products.presenter.config.ModelMapperConfig;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.request.ProductRequest;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.ProductItemWishlistResponse;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
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

    public WishlistResponse toWishlistDtoFrom(Wishlist entity) {
        return this.modelMapperConfig.modelMapper().map(entity, WishlistResponse.class);
    }

    public Wishlist toWishlistFrom(WishlistResponse entity) {
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

    public ProductItemWishlistResponse toProductItemWishlistDtoFrom(ItemWishlist entity) {
        return this.modelMapperConfig.modelMapper().map(entity, ProductItemWishlistResponse.class);
    }

}
