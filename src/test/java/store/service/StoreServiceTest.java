package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.config.StoreConfig;
import store.domain.Product;
import store.domain.Stock;
import store.dto.ProductDto;

class StoreServiceTest {

    private final StoreService storeService = StoreConfig.getStoreService();
    private final ClearService clearService = StoreConfig.getClearService();

    @ParameterizedTest
    @DisplayName("일반 재고가 추가되는지 확인하는 테스트")
    @ValueSource(longs = {1, 100, 100000, 100000000})
    void test1(long quantity) {
        // given
        ProductDto productDto = new ProductDto("제품", 1000, quantity, null);
        Stock stock = new Stock(quantity, 0);
        Product expected = new Product("제품", 1000, stock, null);

        // when
        Product result = storeService.createProduct(productDto);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @AfterEach
    void cleanUp() {
        clearService.clearFile();
    }

}
