package br.com.luizalabs.wishlist.products.config;


import br.com.luizalabs.wishlist.products.dto.wishlist.ItemWishlistDto;
import br.com.luizalabs.wishlist.products.dto.wishlist.WishlistDto;
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
        return modelMapper;
    }
}
