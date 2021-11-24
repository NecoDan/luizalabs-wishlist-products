package br.com.luizalabs.wishlist.products.core.biz;

import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.data.repositories.WishlistRepository;
import br.com.luizalabs.wishlist.products.shared.WishlistCreator;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistNotFoundException;
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
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class WishlistBusinessTest {

    @InjectMocks
    private WishlistBusiness wishlistService;
    @Mock
    private WishlistRepository wishlistRepository;
    @Captor
    private ArgumentCaptor<Wishlist> wishlistArgumentCaptor;

    @Test
    @DisplayName("must save a complete new wishlist")
    void mustSaveANewWishlist() {

        List<ItemWishlist> itemWishlists = Collections.singletonList(
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Caneta Bic Azul")
        );

        Wishlist wishlistSave = WishlistCreator.createdValidWishListWithItems("Lista de compras particular",
                itemWishlists);

        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlistSave);
        wishlistService.save(wishlistSave);

        verify(wishlistRepository, times(1)).save(wishlistSave);
        assertEquals(wishlistSave.getId(), wishlistService.save(wishlistSave).block(Duration.ofSeconds(60)).getId());
    }

    @Test
    @DisplayName("must delete a wish list by id")
    void mustDeleteWishlistById() {

        List<ItemWishlist> itemWishlists = Collections.singletonList(
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Caneta Bic Azul")
        );

        Wishlist wishlist = WishlistCreator.createdValidWishListWithItems("lista", itemWishlists);
        String id = wishlist.getId();

        wishlistService.delete(id);
        verify(wishlistRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("should search wishlist findById")
    void shouldSearchWishlistById() {

        List<ItemWishlist> itemWishlists = Collections.singletonList(
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Caneta Bic Azul")
        );

        Wishlist wishlist = WishlistCreator.createdValidWishListWithItems("Lista de compras particular",
                itemWishlists);

        String id = wishlist.getId();

        when(wishlistRepository.findById(any(String.class))).thenReturn(Optional.of(wishlist));
        wishlistService.findById(id);

        verify(wishlistRepository, times(1)).findById(id);
        assertEquals(wishlist.getId(), wishlistService.findById(id).block(Duration.ofSeconds(60)).getId());
    }

    @Test
    @DisplayName("must search all wishlists by client id and product id")
    void mustSearchAllWishlistsByClientIdAndProductId() {

        String idClient = UUID.randomUUID().toString();
        UUID idProduct = UUID.randomUUID();

        List<ItemWishlist> itemWishlists = Collections.singletonList(
                WishlistCreator.createdItemWishlist(idProduct,
                        "Caneta Bic Azul")
        );

        List<Wishlist> wishlists = List.of(
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists)
        );

        when(wishlistRepository.findAllWishListClientIdAndProductId(anyString(), anyString()))
                .thenReturn(wishlists);

        List<Wishlist> wishlistResult = wishlistService
                .findAllWishListClientIdAndProductId(anyString(), anyString());

        int expectedSize = wishlists.size();
        int resultSize = wishlistResult.size();

        int expectedSumProducts = sumTotalProductsFromElements(wishlists);
        int resultSumProducts = sumTotalProductsFromElements(wishlistResult);

        assertTrue(Objects.equals(expectedSize, resultSize)
                && Objects.equals(expectedSumProducts, resultSumProducts));
    }

    @Test
    @DisplayName("must search all wishlists by client id")
    void mustSearchAllWishlistsByClientId() {

        String idClient = UUID.randomUUID().toString();
        List<ItemWishlist> itemWishlists = Collections.singletonList(
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Caneta Bic Azul")
        );

        List<Wishlist> wishlists = List.of(
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists),
                createWishlistUniqueClient(idClient, itemWishlists)
        );

        when(wishlistRepository.findAllByClientId(anyString())).thenReturn(wishlists);
        Flux<Wishlist> wishlistFlux = Flux.fromIterable(wishlists);
        List<Wishlist> wishlistResult = Objects.requireNonNull(wishlistService.findAllByClientId(anyString())
                .collectList().block());

        int expectedSize = Objects.requireNonNull(wishlistFlux.collectList().block()).size();
        int resultSize = wishlistResult.size();

        int expectedSumProducts = sumTotalProductsFromElements(wishlists);
        int resultSumProducts = sumTotalProductsFromElements(wishlistResult);

        assertTrue(Objects.equals(expectedSize, resultSize)
                && Objects.equals(expectedSumProducts, resultSumProducts));
    }

    private Integer sumTotalProductsFromElements(List<Wishlist> wishlists) {
        return wishlists.stream().mapToInt(Wishlist::getTotalProducts).sum();
    }

    @Test
    @DisplayName("should search wishlist findById returning error")
    void shouldSearchWishlistByIdReturningError() {

        when(wishlistRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(WishlistNotFoundException.class,
                () -> wishlistService.findById(UUID.randomUUID().toString()).block());
    }

    @Test
    @DisplayName("must search all wishlists returning error")
    void mustSearchAllWishlistsReturningError() {

        when(wishlistRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(WishlistNotFoundException.class,
                () -> wishlistService.findAll().blockFirst());
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

    private Wishlist createWishlistUniqueClient(String idClient, List<ItemWishlist> itemWishlists) {
        Wishlist wishlist = WishlistCreator.createdValidWishListWithItems(UUID.randomUUID().toString(), itemWishlists);
        wishlist.setClientId(idClient);
        return wishlist;
    }

}
