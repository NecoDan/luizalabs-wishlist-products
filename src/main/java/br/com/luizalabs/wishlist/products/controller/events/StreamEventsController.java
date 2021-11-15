package br.com.luizalabs.wishlist.products.controller.events;

import br.com.luizalabs.wishlist.products.model.Wishlist;
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

/**
 * @author Daniel Santos
 * @since 13/11/2021
 */
@RestController
@RequestMapping("/v1/events-stream")
@Slf4j
@RequiredArgsConstructor
public class StreamEventsController {

    //private final IPautaReportService pautaReportService;
    private static final Integer DURATION_SECONDS = 5;

    @GetMapping(value = "/wishlist/produces", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Searching all existing wishlist(s)", tags = "events-stream")
    public Flux<Tuple2<Long, Wishlist>> getWishlistByEvents() {
        log.info("Running event stream wishlist(s)...");
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(DURATION_SECONDS));
        Flux<Wishlist> events = null;//pautaReportService.getAll();
        return Flux.zip(interval, events);
    }
}
