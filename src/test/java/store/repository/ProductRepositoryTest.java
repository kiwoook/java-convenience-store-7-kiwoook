package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.repository.impl.ProductRepository;

class ProductRepositoryTest {

    MapRepository<Product> productRepository = new ProductRepository();

    @Test
    @DisplayName("리스트 반환 테스트")
    void test1() {
        // given
        Product product1 = new Product("apple", 1000, null);
        Product product2 = new Product("banana", 500, null);
        productRepository.save("apple", product1);
        productRepository.save("banana", product2);

        // when
        List<Product> products = productRepository.getAll();

        // then
        assertThat(products).containsExactly(product1, product2);
    }

}
