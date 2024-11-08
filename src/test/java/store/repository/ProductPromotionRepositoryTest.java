package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.vo.ProductName;
import store.repository.impl.ProductRepositoryImpl;

class ProductPromotionRepositoryTest {

    ProductRepository productRepository = new ProductRepositoryImpl();

    @Test
    @DisplayName("리스트 반환 테스트")
    void test1() {
        // given
        ProductName productName1 = ProductName.create("제품1");
        ProductName productName2 = ProductName.create("제품2");
        Product product1 = new Product(productName1, 1000, null);
        Product product2 = new Product(productName2, 500, null);
        List<Product> productList = List.of(product1, product2);
        Products expect = new Products(productList);

        productRepository.save(productName1, product1);
        productRepository.save(productName2, product2);

        // when
        Products result = productRepository.getAll();

        // then
        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("리스트 저장 후 비어지는지 테스트")
    void test2() {
        // given
        ProductName productName1 = ProductName.create("제품1");
        ProductName productName2 = ProductName.create("제품2");
        Product product1 = new Product(productName1, 1000, null);
        Product product2 = new Product(productName2, 500, null);

        productRepository.save(productName1, product1);
        productRepository.save(productName2, product2);

        // when
        productRepository.clear();
        Products result = productRepository.getAll();

        // then
        assertThat(result.size()).isZero();
    }

}
