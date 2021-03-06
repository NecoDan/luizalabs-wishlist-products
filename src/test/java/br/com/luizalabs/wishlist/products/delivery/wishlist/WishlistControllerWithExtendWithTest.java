package br.com.luizalabs.wishlist.products.delivery.wishlist;


import br.com.luizalabs.wishlist.products.core.biz.GenerateWishlistBusiness;
import br.com.luizalabs.wishlist.products.core.biz.ValidateWishlistBusiness;
import br.com.luizalabs.wishlist.products.core.biz.WishlistBusiness;
import br.com.luizalabs.wishlist.products.core.biz.WishlistReportBusiness;
import br.com.luizalabs.wishlist.products.core.usercase.*;
import br.com.luizalabs.wishlist.products.data.entities.ItemWishlist;
import br.com.luizalabs.wishlist.products.data.entities.Wishlist;
import br.com.luizalabs.wishlist.products.data.repositories.WishlistRepository;
import br.com.luizalabs.wishlist.products.delivery.configuration.ModelMapperConfig;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.delivery.entities.wishlist.response.WishlistResponse;
import br.com.luizalabs.wishlist.products.shared.WishlistCreator;
import br.com.luizalabs.wishlist.products.shared.WishlistMapper;
import br.com.luizalabs.wishlist.products.shared.properties.ParamLimitProductProperties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@AutoConfigureWebTestClient(timeout = "100000")
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WishlistController.class)
@Import({ModelMapperConfig.class, ModelMapper.class,
        GenerateWishlistBusiness.class, WishlistReportBusiness.class, ParamLimitProductProperties.class,
        WishlistMapper.class, ValidateWishlistBusiness.class, GetOneProductByClientIdProductIdUseCaseImpl.class,
        GetAllWishlistUseCaseImpl.class, GetOneByIdWishlistUseCaseImpl.class,
        PostOneWishlistUseCaseImpl.class, AddSingleProductWishlistUseCaseImpl.class,
        DeleteProductWishlistUseCaseImpl.class})
public class WishlistControllerWithExtendWithTest {

    @MockBean
    WishlistRepository wishlistRepository;

    @MockBean
    WishlistBusiness wishlistService;

    @Autowired
    private WebTestClient webTestClient;

    private ModelMapper modelMapper;

    private static final String URI = "/v1/wishlist/";
    private final static String LIST_NAME = "Lista desejos";
    private final Wishlist wishlist = WishlistCreator.createdValidWishListToBeSaved(LIST_NAME);

    /**
     * Defini????o pra mantermos a certeza que o c??digo n??o est?? sendo bloqueado por nenhuma thread ou bloqueando
     * nenhuma thread
     */
    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    void initClient() {
        BlockHound.install();
        this.modelMapper = WishlistCreator.createModelMapperForTests();
    }

    /**
     * M??todo de teste que identifica se o BlockHound est?? sendo carregado em todas as execu????es dos testes desta classe
     */
    @Test
    void blockHoundWorks() {

        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0); //NOSONAR
                return "";
            });

            Schedulers.parallel().schedule(task);
            task.get(100, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    @DisplayName("find wishlist by id")
    void findByIdWishlistReturnMonoPautaWhenSuccessful() {

        var id = wishlist.getId();
        var wishlistResponse = WishlistCreator.createModelMapperForTests()
                .map(wishlist, WishlistResponse.class);

        Mockito.when(wishlistService.findById(id)).thenReturn(Mono.just(wishlist));

        webTestClient.get()
                .uri(URI + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(WishlistResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    Assert.isTrue(response.get(0).getId().equals(wishlistResponse.getId()), wishlist.getId());
                    Assert.isTrue(response.get(0).getTitle().equals(wishlistResponse.getTitle()), wishlist.getTitle());
                    Assert.isTrue(response.get(0).getClientId().equals(wishlistResponse.getClientId()), wishlist.getClientId());
                    Assert.notNull(response.get(0).getDtCreated());
                    Assert.notNull(response.get(0).getItemWishlist());
                });

        Mockito.verify(wishlistService, times(1)).findById(id);
    }

    @Test
    @DisplayName("listAll returns a flux of wish list 00")
    void listAllFlavor2ReturnFluxOfWishlistWhenSuccessful() {

        var wishlistResponse = WishlistCreator.createModelMapperForTests()
                .map(wishlist, WishlistResponse.class);

        Mockito.when(wishlistService.findAll())
                .thenReturn(Flux.just(wishlist,
                        WishlistCreator.createdValidWishListToBeSaved(UUID.randomUUID().toString()),
                        WishlistCreator.createdValidWishListToBeSaved(UUID.randomUUID().toString()),
                        WishlistCreator.createdValidWishListToBeSaved(UUID.randomUUID().toString())
                ));

        webTestClient.get()
                .uri(URI)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(WishlistResponse.class)
                .hasSize(4)
                .contains(wishlistResponse);
    }

    @Test
    @DisplayName("listAll returns a flux of wish list 01")
    void listAllReturnFluxOfWishlistWhenSuccessful() {

        var wishlistResponse = WishlistCreator.createModelMapperForTests()
                .map(wishlist, WishlistResponse.class);

        Mockito.when(wishlistService.findAll())
                .thenReturn(Flux.just(wishlist,
                        WishlistCreator.createdValidWishListToBeSaved(UUID.randomUUID().toString()),
                        WishlistCreator.createdValidWishListToBeSaved(UUID.randomUUID().toString()),
                        WishlistCreator.createdValidWishListToBeSaved(UUID.randomUUID().toString())
                ));

        webTestClient.get()
                .uri(URI)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(wishlistResponse.getId())
                .jsonPath("$.[0].title").isEqualTo(wishlistResponse.getTitle())
                .jsonPath("$.[0].client_id").isEqualTo(wishlistResponse.getClientId());
    }

    private List<ItemWishlistRequest> getListItemWishlistRequest(List<ItemWishlist> itemWishlist) {
        return itemWishlist.stream().filter(Objects::nonNull)
                .map(this::getItemWishlistRequestFrom)
                .collect(Collectors.toList());
    }

    private ItemWishlistRequest getItemWishlistRequestFrom(ItemWishlist itemWishlist) {
        return modelMapper.map(itemWishlist, ItemWishlistRequest.class);
    }
}
