package br.com.luizalabs.wishlist.products.delivery.events;

import br.com.luizalabs.wishlist.products.shared.WishlistMapper;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.core.port.WishlistReportPort;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;

//import io.swagger.v3.oas.annotations.Operation;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@RestController
@RequestMapping("/v1/events-stream")
@Slf4j
@RequiredArgsConstructor
public class StreamEventsController {

    private final WishlistReportPort wishlistReportService;
    private final WishlistMapper wishlistMapper;
    private static final Integer DURATION_SECONDS = 5;

    @GetMapping(value = "/wishlist/produces", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Returns all existing wishlists", tags = "events-stream")
    public Flux<Tuple2<Long, WishlistResponse>> getWishlistByEvents() {
        log.info("[luizalabs-wishlist-products] [events]");
        log.info("[luizalabs-wishlist-products] [events] | Running event stream wishlist(s)");

        var interval = Flux.interval(Duration.ofSeconds(DURATION_SECONDS));
        log.info("[luizalabs-wishlist-products] [events] | Intervalo de tempo: {}", interval);

        var events = wishlistReportService.getAll()
                .map(wishlistMapper::toWishlistResponseFrom);
        log.info("[luizalabs-wishlist-products] [events] | Response: {}", events);

        return Flux.zip(interval, events);
    }
}
