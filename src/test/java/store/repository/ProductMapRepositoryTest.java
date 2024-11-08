package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.repository.impl.ProductMapRepository;

class ProductMapRepositoryTest {

    MapRepository<Product> productMapRepository = new ProductMapRepository();

    @Test
    @DisplayName("리스트 반환 테스트")
    void test1() {
        // given
        Product product1 = new Product("apple", 1000, null);
        Product product2 = new Product("banana", 500, null);
        productMapRepository.save("apple", product1);
        productMapRepository.save("banana", product2);

        // when
        List<Product> products = productMapRepository.getAll();

        // then
        assertThat(products).containsExactly(product1, product2);
    }

    @Test
    @DisplayName("리스트 저장 후 비어지는지 테스트")
    void test2(){
        // given
        Product product1 = new Product("apple", 1000, null);
        Product product2 = new Product("banana", 500, null);
        productMapRepository.save("apple", product1);
        productMapRepository.save("banana", product2);

        // when
        productMapRepository.clear();
        List<Product> result = productMapRepository.getAll();

        // then
        assertThat(result).isEmpty();
    }

}
