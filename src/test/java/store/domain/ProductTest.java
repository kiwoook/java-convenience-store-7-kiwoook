package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.vo.ProductName;
import store.exception.InvalidFormatException;

class ProductTest {

    @Test
    @DisplayName("제품의 가격이 다르면 에러를 반환한다.")
    void test1() {
        Product product = new Product(ProductName.create("제품"), 1000, null);

        assertThrows(InvalidFormatException.class, () -> {
            product.validPrice(2000);
        });
    }

    @Test
    @DisplayName("프로모션이 존재하는데 다른 프로모션이 주입되면 에러를 발생한다")
    void test2() {
        Promotion promotion = new Promotion("1+2", 1, 2, null, null);
        Promotion promotion2 = new Promotion("2+1", 2, 1, null, null);
        ProductName productName = ProductName.create("제품");
        Product product = new Product(productName, 1000, promotion);
        assertThrows(InvalidFormatException.class, () -> {
            product.updatePromotion(promotion2);
        });
    }

    @Test
    @DisplayName("문자열 반환 테스트")
    void test3() {
        Stock stock = new Stock(10, 10);
        Promotion promotion = new Promotion("탄산2+1", 2, 1, null, null);
        ProductName productName = ProductName.create("콜라");
        Product product = new Product(productName, 1000, stock, promotion);

        // when && then
        assertThat(product.toString()).contains("- 콜라 1,000원 10개 탄산2+1", "- 콜라 1,000원 10개");
    }

    @Test
    @DisplayName("재고가 0이면 재고 없음으로 반환하여야 한다.")
    void test4() {
        // given
        Stock stock = new Stock(0, 9);
        Promotion promotion = new Promotion("MD추천상품", 1, 1, null, null);
        ProductName productName = ProductName.create("오렌지주스");
        Product product = new Product(productName, 1800, stock, promotion);

        // when && then
        assertThat(product.toString()).contains("- 오렌지주스 1,800원 9개 MD추천상품", "- 오렌지주스 1,800원 재고 없음");
    }
}
