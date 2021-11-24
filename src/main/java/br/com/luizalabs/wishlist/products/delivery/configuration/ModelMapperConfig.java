package br.com.luizalabs.wishlist.products.delivery.configuration;


import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ProductRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.ItemWishlistResponse;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Wishlist.class, WishlistResponse.class);
        modelMapper.createTypeMap(ItemWishlist.class, ItemWishlistResponse.class);
        modelMapper.createTypeMap(ItemWishlistRequest.class, ItemWishlist.class);
        modelMapper.createTypeMap(WishlistRequest.class, Wishlist.class);
        modelMapper.createTypeMap(WishlistRequest.class, ProductRequest.class);

        return modelMapper;
    }
}
