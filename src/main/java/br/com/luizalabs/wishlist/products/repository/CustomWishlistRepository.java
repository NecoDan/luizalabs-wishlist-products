package br.com.luizalabs.wishlist.products.repository;


import br.com.luizalabs.wishlist.products.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class CustomWishlistRepository implements ICustomWishlistRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Wishlist> findAllByClientId(String idClient) {

        Query query = new Query(Criteria.where("client_id").is(idClient));
        return this.mongoTemplate.find(query, Wishlist.class);
    }
}
