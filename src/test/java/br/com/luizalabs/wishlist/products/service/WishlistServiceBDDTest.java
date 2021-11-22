package br.com.luizalabs.wishlist.products.service;

import br.com.luizalabs.wishlist.products.WishlistCreator;
import br.com.luizalabs.wishlist.products.shared.exceptions.WishlistNotFoundException;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.repository.WishlistRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@ExtendWith(SpringExtension.class)
public class WishlistServiceBDDTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private final static String LIST_NAME = "Lista tênis comprar";
    private final Wishlist wishlist = WishlistCreator.createdValidWishListToBeSaved(LIST_NAME);

    /**
     * Definição pra mantermos a certeza que o código não está sendo bloqueado por nenhuma thread ou bloqueando
     * nenhuma thread
     */
    @BeforeAll
    public static void BlockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
//
//        BDDMockito.when(Flux.just(wishlistRepository.findAll()))
//                .thenReturn(Flux.just(wishlist));
//
//        BDDMockito.when(wishlistRepository.findById(ArgumentMatchers.anyString()))
//                .thenReturn(Mono.just(wishlist));

        BDDMockito.when(Mono.just(wishlistRepository.save(WishlistCreator.createdValidWishListToBeSaved(LIST_NAME))))
                .thenReturn(Mono.just(wishlist));

//        BDDMockito.when(wishlistRepository.delete(ArgumentMatchers.any(Wishlist.class)))
//                .thenReturn(Mono.empty());
    }

    /**
     * Método de teste que identifica se o BlockHound está sendo carregado em todas as execuções dos testes desta classe
     */
    @Test
    void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0); //NOSONAR
                return "";
            });

            Schedulers.parallel().schedule(task);
            task.get(30, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    //@Test
    //@DisplayName("save creates an wishlist when successfuly")
    void saveCreatesWishlistWhenSuccessful() {

        Wishlist wishlistSaved = WishlistCreator.createdValidWishListToBeSaved(LIST_NAME);

        StepVerifier.create(wishlistService.save(wishlistSaved))
                .expectSubscription()
                .expectNext(wishlist)
                .verifyComplete();
    }

    @Test
    @DisplayName("update save updated wishlist returns empty mono when successful")
    void updateSaveUpdatedWishlistWhenSuccessful() {

        Wishlist wishlistUpdated = getWishlistGeneral();

        StepVerifier.create(wishlistService.update(wishlistUpdated))
                .expectSubscription()
                .expectNoEvent(Duration.ZERO);
    }

    @Test
    @DisplayName("findById returns a Mono with wishlist when it exists")
    void findByIdReturnMonoWishlistWhenSuccessful() {

        StepVerifier.create(wishlistService.findById(UUID.randomUUID().toString()))
                .expectSubscription()
                .expectNext(wishlist)
                .verifyComplete();
    }

    @Test
    @DisplayName("findAll returns a flux of wishlist")
    void findAllReturnFluxOfWishlistWhenSuccessful() {
        StepVerifier.create(wishlistService.findAll())
                .expectSubscription()
                .expectNext(wishlist)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono error when pauta does not exist")
    void findByIdReturnMonoErrorWhenEmptyMonoIsReturned() {
        BDDMockito.when(Mono.just(wishlistRepository.findById(ArgumentMatchers.anyString())))
                .thenReturn(Mono.empty());

        StepVerifier.create(wishlistService.findById(UUID.randomUUID().toString()))
                .expectSubscription()
                .expectError(WishlistNotFoundException.class)
                .verify();
    }

    private Wishlist getWishlistGeneral() {
        return WishlistCreator.createdValidWishListNoItems(LIST_NAME);
    }

    private List<ItemWishlist> getItemWishlistsGeneral() {

        return Collections.singletonList(WishlistCreator.createdItemWishlist(UUID.randomUUID(),
                "Tenis Adidas Serie XXXX"));
    }
}
