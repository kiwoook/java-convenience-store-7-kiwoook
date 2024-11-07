package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static store.enums.ErrorMessage.EXCEED_PURCHASE;
import static store.enums.ErrorMessage.NOT_EXIST_PRODUCT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Stock;
import store.repository.impl.ProductMapRepository;
import store.repository.impl.PurchaseInfosRepository;
import store.repository.impl.PurchaseVerificationRepository;
import store.service.impl.PurchaseServiceImpl;

class PurchaseServiceTest {

    private ProductMapRepository productRepository;
    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    void setUp() {
        productRepository = new ProductMapRepository();
        PurchaseInfosRepository purchaseInfosSingleRepository = new PurchaseInfosRepository();
        PurchaseVerificationRepository purchaseVerificationRepository = new PurchaseVerificationRepository();
        purchaseService = new PurchaseServiceImpl(purchaseInfosSingleRepository,
                productRepository, purchaseVerificationRepository);
    }

    @Test
    @DisplayName("해당 제품이 없으면 에러를 반환")
    void validateQuantityTest1() {
        // given
        String productName = "제품2";
        Product product = new Product(productName, 1000, null);

        productRepository.save(productName, product);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseService.create("[제품1-10]");
        });

        assertThat(exception.getMessage()).isEqualTo(NOT_EXIST_PRODUCT.getMessage());
    }

    @Test
    @DisplayName("제품이 존재하나 재고가 0일 경우 에러를 반환")
    void validateQuantityTest2() {
        // given
        String productName = "제품1";
        Product product = new Product(productName, 1000, new Stock(), null);

        productRepository.save(productName, product);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseService.create("[제품1-1]");
        });

        assertThat(exception.getMessage()).isEqualTo(EXCEED_PURCHASE.getMessage());
    }

    @Test
    @DisplayName("일반 재고가 존재할 경우 정상 처리")
    void validateQuantityTest3() {
        String productName = "제품1";
        Product product = new Product(productName, 1000, new Stock(5, 0), null);

        productRepository.save(productName, product);

        // when
        purchaseService.create("[제품1-5]");
    }

    @Test
    @DisplayName("프로모션 재고가 존재할 경우 정상 처리")
    void validateQuantityTest4() {
        String productName = "제품1";
        Product product = new Product(productName, 1000, new Stock(0, 5), null);

        productRepository.save(productName, product);

        // when
        purchaseService.create("[제품1-5]");
    }

}
