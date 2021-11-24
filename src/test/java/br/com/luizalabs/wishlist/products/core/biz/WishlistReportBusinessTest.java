package br.com.luizalabs.wishlist.products.core.biz;

import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.shared.WishlistCreator;
import br.com.luizalabs.wishlist.products.shared.WishlistMapper;
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
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Daniel Santos
 * @since 24/11/2021
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class WishlistReportBusinessTest {

    @InjectMocks
    private WishlistReportBusiness wishlistReportBusiness;
    @Mock
    private WishlistBusiness wishlistBusiness;
    @Mock
    private WishlistMapper wishlistMapper;
    @Captor
    private ArgumentCaptor<Wishlist> wishlistArgumentCaptor;

    @Test
    @DisplayName("should search wishlist getAll")
    void shouldGetAllWishlist() {

        List<ItemWishlist> itemWishlists = buildItemWishlist();

        List<Wishlist> wishlists = List.of(
                WishlistCreator.createdValidWishListWithItems(UUID.randomUUID().toString(), itemWishlists),
                WishlistCreator.createdValidWishListWithItems(UUID.randomUUID().toString(), itemWishlists),
                WishlistCreator.createdValidWishListWithItems(UUID.randomUUID().toString(), itemWishlists),
                WishlistCreator.createdValidWishListWithItems(UUID.randomUUID().toString(), itemWishlists)
        );

        when(wishlistBusiness.findAll()).thenReturn(Flux.fromIterable(wishlists));
        List<Wishlist> wishlistResult = wishlistReportBusiness.getAll().collectList().block();

        verify(wishlistBusiness, times(1)).findAll();

        int expectedSize = wishlists.size();
        int resultSize = wishlistResult.size();

        int expectedSumProducts = sumTotalProductsFromElements(wishlists);
        int resultSumProducts = sumTotalProductsFromElements(wishlistResult);

        assertTrue(Objects.equals(expectedSize, resultSize)
                && Objects.equals(expectedSumProducts, resultSumProducts));
    }

    @Test
    @DisplayName("should search wishlist getOneBy")
    void shouldSearchWishlistById() {

        Wishlist wishlist = WishlistCreator.createdValidWishListWithItems("Lista de compras particular",
                buildItemWishlist());

        String id = wishlist.getId();

        when(wishlistBusiness.findById(any(String.class))).thenReturn(Mono.just(wishlist));
        wishlistReportBusiness.getOneBy(id);

        verify(wishlistBusiness, times(1)).findById(id);
        assertEquals(wishlist.getId(), wishlistReportBusiness.getOneBy(id).block(Duration.ofSeconds(60)).getId());
    }

    private List<ItemWishlist> buildItemWishlist() {

        return List.of(
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Caneta Bic Azul"),
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Caneta Bic Vermelho"),
                WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                        "Caneta Bic Preto")
        );
    }

    private Wishlist buildWishlistFrom(String clientId, List<ItemWishlist> itemWishlists) {

        Wishlist wishlist = WishlistCreator.createdValidWishListWithItems(UUID.randomUUID().toString(), itemWishlists);
        wishlist.setClientId(clientId);
        return wishlist;
    }

    private ItemWishlist buildItemWishListBy(UUID productId, String productName) {

        return WishlistCreator.createdItemWishlist(productId, productName);
    }

    private Integer sumTotalProductsFromElements(List<Wishlist> wishlists) {

        return wishlists.stream().mapToInt(Wishlist::getTotalProducts).sum();
    }
}
