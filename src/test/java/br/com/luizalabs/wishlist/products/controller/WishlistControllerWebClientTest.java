package br.com.luizalabs.wishlist.products.controller;

import br.com.luizalabs.wishlist.products.WishlistCreator;
import br.com.luizalabs.wishlist.products.broker.WishlistMapper;
import br.com.luizalabs.wishlist.products.configuration.AdviceControllerConfig;
import br.com.luizalabs.wishlist.products.controller.api.WishlistController;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.ItemWishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.request.WishlistRequest;
import br.com.luizalabs.wishlist.products.dto.wishlist.response.WishlistDto;
import br.com.luizalabs.wishlist.products.model.ItemWishlist;
import br.com.luizalabs.wishlist.products.model.Wishlist;
import br.com.luizalabs.wishlist.products.properties.TransactionProperties;
import br.com.luizalabs.wishlist.products.repository.WishlistRepository;
import br.com.luizalabs.wishlist.products.service.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Daniel Santos
 * @since 15/11/2021
 */
@WebFluxTest
@ContextConfiguration(classes = TestConfiguration.class)
@AutoConfigureWebTestClient(timeout = "10000")
public class WishlistControllerWebClientTest {

    private WishlistRepository wishlistRepositoryMock;
    private WishlistService wishlistService;
    private WishlistReportService wishlistReportService;
    private GenerateWishlistService generateWishlistServiceMock;
    private ModelMapper modelMapper;
    private WishlistMapper wishlistMapper;
    private TransactionProperties transactionPropertiesMock;
    private ValidateWishlistService validateWishlistMock;

    @Autowired
    private WebTestClient webTestClient;

    private static final String URI = "/v1/wishlist/";
    private final static String LIST_NAME = "Lista desejos";
    private final Wishlist wishlist = WishlistCreator.createdValidWishListToBeSaved(LIST_NAME);

    /**
     * Definição pra mantermos a certeza que o código não está sendo bloqueado por nenhuma thread ou bloqueando
     * nenhuma thread
     */
    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    void initClient() {

        this.modelMapper = WishlistCreator.createModelMapperForTests();

        this.wishlistMapper = Mockito.mock(WishlistMapper.class);
        this.wishlistRepositoryMock = Mockito.mock(WishlistRepository.class);
        this.wishlistService = new WishlistService(wishlistRepositoryMock);
        this.wishlistReportService = new WishlistReportService(wishlistService, wishlistMapper);

        this.validateWishlistMock = Mockito.mock(ValidateWishlistService.class);
        this.transactionPropertiesMock = Mockito.mock(TransactionProperties.class);
        this.generateWishlistServiceMock = new GenerateWishlistService(wishlistService, validateWishlistMock,
                wishlistMapper);

        WishlistController wishlistController = new WishlistController(generateWishlistServiceMock,
                wishlistReportService, wishlistMapper);

        webTestClient = WebTestClient
                .bindToController(wishlistController)
                .controllerAdvice(AdviceControllerConfig.class)
                .build();
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

            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    void saveCreatesWishlistWhenSuccessful() {

        var wishlistSaved = this.wishlist;

        var wishlistRequest = WishlistRequest.builder()
                .title(wishlistSaved.getTitle())
                .clientId(wishlistSaved.getClientId())
                .itemWishlist(getListItemWishlistRequest(wishlistSaved.getItemWishlist()))
                .build();

        var wishlistResponse = WishlistCreator.createModelMapperForTests()
                .map(wishlistSaved, WishlistDto.class);

        Mockito.when(wishlistMapper.toWishlistFromRequest(any(WishlistRequest.class))).thenReturn(wishlistSaved);
        Mockito.when(wishlistRepositoryMock.save(any(Wishlist.class))).thenReturn(wishlistSaved);
        Mockito.when(wishlistMapper.toWishlistDtoFrom(any(Wishlist.class))).thenReturn(wishlistResponse);

        var response = webTestClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(wishlistRequest))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(WishlistDto.class)
                .getResponseBody()
                .blockFirst();

        assertNotNull(response);
        assertThat(response.getId()).isEqualTo(wishlistSaved.getId());
        assertThat(response.getTitle()).isEqualTo(wishlistSaved.getTitle());
        assertThat(response.getClientId()).isEqualTo(wishlistSaved.getClientId());
        assertThat(response.getItemWishlist().size()).isEqualTo(wishlistSaved.getItemWishlist().size());
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
