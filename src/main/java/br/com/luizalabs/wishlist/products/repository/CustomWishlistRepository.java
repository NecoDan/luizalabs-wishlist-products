package br.com.luizalabs.wishlist.products.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomWishlistRepository implements ICustomWishlistRepository {

    private final ReactiveMongoTemplate mongoTemplate;

}
