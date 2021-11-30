package br.com.luizalabs.wishlist.products.delivery.app;

//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel Santos
 * @since 13/11/2021
 * Home redirection to swagger api documentation
 */
@RestController
@Slf4j
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AppController {

    @Value("${app.message}")
    private String appMessage;

    @GetMapping
    @Operation(summary = "Get api its works", responses = {
            @ApiResponse(description = "Get message success", responseCode = "200",
                    content = @Content(mediaType = "application/json"))
    }, tags = "app")
    public ResponseEntity<String> getAppMessage() {
        log.info("[luizalabs-wishlist-products] [events] | {}", appMessage);
        return ResponseEntity.ok(appMessage);
    }
}
