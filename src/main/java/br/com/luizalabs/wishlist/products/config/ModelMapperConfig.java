package br.com.luizalabs.wishlist.products.config;


import br.com.luizalabs.wishlist.products.dto.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.ProductRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.ItemWishlistDto;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
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

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Wishlist.class, WishlistDto.class);
        modelMapper.createTypeMap(ItemWishlist.class, ItemWishlistDto.class);
        modelMapper.createTypeMap(ItemWishlistRequest.class, ItemWishlist.class);
        modelMapper.createTypeMap(WishlistRequest.class, Wishlist.class);
        modelMapper.createTypeMap(WishlistRequest.class, ProductRequest.class);

        return modelMapper;
    }
}
