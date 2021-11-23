package br.com.luizalabs.wishlist.products.presenter.rest.api.wishlist;


import br.com.luizalabs.wishlist.products.data.db.jpa.entities.Wishlist;
import br.com.luizalabs.wishlist.products.presenter.rest.api.entities.response.WishlistResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@RestController
@RequestMapping("/v1/wishlist")
@CrossOrigin(origins = "*")
public interface WishlistResource {

    @GetMapping
    CompletableFuture<List<Wishlist>> findAll();

    //    @GetMapping("/{id}")
    CompletableFuture<Mono<WishlistResponse>> findById(@PathVariable("id") String id);

}
