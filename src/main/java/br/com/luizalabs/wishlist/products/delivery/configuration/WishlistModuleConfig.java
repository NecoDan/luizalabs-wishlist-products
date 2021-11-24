package br.com.luizalabs.wishlist.products.delivery.configuration;


import br.com.luizalabs.wishlist.products.core.biz.GenerateWishlistBusiness;
import br.com.luizalabs.wishlist.products.core.biz.ValidateWishlistBusiness;
import br.com.luizalabs.wishlist.products.core.biz.WishlistBusiness;
import br.com.luizalabs.wishlist.products.core.biz.WishlistReportBusiness;
import br.com.luizalabs.wishlist.products.core.usercase.*;
import br.com.luizalabs.wishlist.products.data.repositories.WishlistRepository;
import br.com.luizalabs.wishlist.products.shared.WishlistMapper;
import br.com.luizalabs.wishlist.products.shared.properties.ParamLimitProductProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class WishlistModuleConfig {

    @Qualifier("repository_module")
    private final WishlistRepository wishlistRepository;

    private final ParamLimitProductProperties properties;

    @Qualifier("wishlistBusiness_module")
    @Primary
    @Bean
    public WishlistBusiness createWishlistBusiness() {
        return new WishlistBusiness(wishlistRepository);
    }

    @Qualifier("validateWishlistBusiness_module")
    @Primary
    @Bean
    public ValidateWishlistBusiness createValidateWishlistBusiness() {
        return new ValidateWishlistBusiness(properties);
    }

    @Bean
    @Primary
    @Qualifier("generateWishlistBusiness_module")
    public GenerateWishlistBusiness createGenerateWishlistBusiness() {
        return new GenerateWishlistBusiness(createWishlistBusiness(), createValidateWishlistBusiness(),
                createWishlistMapper());
    }

    @Bean
    public WishlistMapper createWishlistMapper() {
        var modelMapperConfig = new ModelMapperConfig();
        return new WishlistMapper(modelMapperConfig);
    }

    @Bean
    @Qualifier(("wishlistReportBusiness_module"))
    @Primary
    public WishlistReportBusiness createWishlistReportBusiness() {
        return new WishlistReportBusiness(createWishlistBusiness(), createWishlistMapper());
    }

    @Bean
    public GetAllWishlistUseCaseImpl createGetAllWishlistUseCase() {
        return new GetAllWishlistUseCaseImpl(createWishlistReportBusiness());
    }

    @Bean
    public GetOneByIdWishlistUseCaseImpl createGetOneByIdWishlistUseCase() {
        return new GetOneByIdWishlistUseCaseImpl(createWishlistReportBusiness());
    }

    @Bean
    public GetOneProductByClientIdProductIdUseCaseImpl createGetOneProductByClientIdProductIdUseCase() {
        return new GetOneProductByClientIdProductIdUseCaseImpl(createWishlistReportBusiness());
    }

    @Bean
    public PostOneWishlistUseCaseImpl createPostOneWishlistUseCaseImpl() {
        return new PostOneWishlistUseCaseImpl(createGenerateWishlistBusiness());
    }

    @Bean
    public AddSingleProductWishlistUseCaseImpl createAddSingleProductWishlistUseCaseImpl() {
        return new AddSingleProductWishlistUseCaseImpl(createGenerateWishlistBusiness());
    }

    @Bean
    public DeleteProductWishlistUseCaseImpl createDeleteProductWishlistUseCaseImpl() {
        return new DeleteProductWishlistUseCaseImpl(createGenerateWishlistBusiness());
    }
}
