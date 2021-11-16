package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.WishlistCreator;
import br.com.luizalabs.wishlist.products.exceptions.WishlistNotFoundException;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.repository.WishlistRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    @InjectMocks
    private WishlistService wishlistService;
    @Mock
    private WishlistRepository wishlistRepository;
    @Captor
    private ArgumentCaptor<Wishlist> wishlistArgumentCaptor;

    @Test
    void mustSaveANewWishlist() {

        List<ItemWishlist> itemWishlists = Collections.singletonList(WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                "Caneta Bic Azul"));

        Wishlist wishlistSave = WishlistCreator.createdValidWishListWithItems("Lista de compras particular", itemWishlists);
        wishlistService.save(wishlistSave);

        verify(wishlistRepository, times(1)).save(wishlistArgumentCaptor.capture());
        assertEquals(wishlistSave, wishlistArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("should search wishlist findById returning error")
    void shouldSearchPautaReturningError() {

        when(wishlistRepository.findById(anyString()))
                .thenReturn(Mono.empty());

        assertThrows(WishlistNotFoundException.class,
                () -> wishlistService.findById(UUID.randomUUID().toString()).block()
        );
    }

    @SneakyThrows
    @Test
    @DisplayName("generate json value from object wishlist")
    void generateJsonValueFromObjectWishlist() {
        String value = WishlistCreator
                .generateJsonValueFromObjectWishlist(WishlistCreator.createdValidWishListToBeSaved("Compras particular"));
        log.info("Result value from object to json: {}", value);
        assertNotNull(value);
    }
}
