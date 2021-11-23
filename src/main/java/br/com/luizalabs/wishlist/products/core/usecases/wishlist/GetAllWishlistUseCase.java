package br.com.luizalabs.wishlist.products.core.usecases.wishlist;

import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.core.usecases.UseCase;
import br.com.luizalabs.wishlist.products.core.usecases.wishlist.repository.WishlistRepository;
import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class GetAllWishlistUseCase extends UseCase<GetAllWishlistUseCase.InputValues, GetAllWishlistUseCase.OutputValues> {

    private WishlistRepository repository;

    @Autowired
    private WishlistMapper wishlistMapper;

    public GetAllWishlistUseCase(WishlistRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return new OutputValues(repository.findAll().collectList().block());
//                .map(wishlistMapper::toWishlistDtoFrom)
//                .doOnComplete(() -> log.info("[luizalabs-wishlist-products] | Returning list of wish list")));
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Wishlist> wishlist;
    }
}
