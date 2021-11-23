package br.com.luizalabs.wishlist.products.presenter.config;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.core.usecases.wishlist.GetAllWishlistUseCase;
import br.com.luizalabs.wishlist.products.core.usecases.wishlist.repository.WishlistRepositoryImpl;
import br.com.luizalabs.wishlist.products.data.db.jpa.repositories.JpaWishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class Module {

    private final JpaWishlistRepository jpaWishlistRepository;

    @Bean
    public WishlistMapper wishlistMapper() {
        ModelMapperConfig modelMapperConfig = new ModelMapperConfig();
        return new WishlistMapper(modelMapperConfig);
    }

    @Bean
    public GetAllWishlistUseCase getAllWishlistUseCase() {
        return new GetAllWishlistUseCase(new WishlistRepositoryImpl(jpaWishlistRepository));
    }

}
