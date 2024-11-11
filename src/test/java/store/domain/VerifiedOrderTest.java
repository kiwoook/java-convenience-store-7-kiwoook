package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.vo.ProductName;
import store.dto.OrderConfirmDto;
import store.enums.Confirmation;

class VerifiedOrderTest {

    @Test
    @DisplayName("프로모션 재고 부족으로 구매를 안한다.")
    void test1() {
        ProductName productName = new ProductName("제품");
        Stock stock = new Stock(15, 10);
        Product product = new Product(productName, 1000, stock, null);
        VerifiedOrder expected = new VerifiedOrder(product, 0);

        OrderConfirmDto confirmDto = new OrderConfirmDto(productName, 10L, -5L);
        Confirmation no = Confirmation.NO;

        VerifiedOrder result = VerifiedOrder.of(product, confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("프로모션을 받는다.")
    void test2() {
        ProductName productName = new ProductName("제품");
        Stock stock = new Stock(0, 15);
        Product product = new Product(productName, 1000, stock, null);
        VerifiedOrder expected = new VerifiedOrder(product, 15);

        OrderConfirmDto confirmDto = new OrderConfirmDto(productName, 10L, 5L);
        Confirmation no = Confirmation.YES;

        VerifiedOrder result = VerifiedOrder.of(product, confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("상관없이 구매한다.")
    void test3() {
        ProductName productName = new ProductName("제품");
        Stock stock = new Stock(15, 15);
        Product product = new Product(productName, 1000, stock, null);

        VerifiedOrder expected = new VerifiedOrder(product, 15);

        OrderConfirmDto confirmDto1 = new OrderConfirmDto(productName, 15L, 5L);
        OrderConfirmDto confirmDto2 = new OrderConfirmDto(productName, 15L, -5L);

        VerifiedOrder result1 = VerifiedOrder.of(product, confirmDto1, Confirmation.NO);
        VerifiedOrder result2 = VerifiedOrder.of(product, confirmDto2, Confirmation.YES);

        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }

    @Test
    @DisplayName("제품의 총 원가를 계산한다.")
    void test4() {
        // 원가는 프로모션 적용이 안된 제품 수량을 말한다.
        long normalQuantity = 15;
        long price = 1000;
        long buyQuantity = 2;
        ProductName productName = new ProductName("제품");
        Promotion promotion = new Promotion(null, buyQuantity, null, null);
        Stock stock = new Stock(normalQuantity, buyQuantity + 1);
        Product product = new Product(productName, price, stock, promotion);
        VerifiedOrder verifiedOrder = VerifiedOrder.of(product, normalQuantity + buyQuantity + 1);

        // then
        long result = verifiedOrder.getTotalOriginalPriceByProduct();

        // when
        assertThat(result).isEqualTo(normalQuantity * price);
    }

    @Test
    @DisplayName("제품의 총구매액을 계산한다.")
    void test5() {
        // given
        long normalQuantity = 15;
        long price = 1000;
        ProductName productName = new ProductName("제품");
        Stock stock = new Stock(normalQuantity, 0);
        Product product = new Product(productName, price, stock, null);
        VerifiedOrder verifiedOrder = VerifiedOrder.of(product, normalQuantity);

        // then
        long result = verifiedOrder.getTotalPrice();

        // when
        assertThat(result).isEqualTo(normalQuantity * price);
    }

    @Test
    @DisplayName("제품의 총할인액을 계산한다.")
    void test6() {
        // 원가는 프로모션 적용이 안된 제품 수량을 말한다.
        long normalQuantity = 15;
        long price = 1000;
        long buyQuantity = 2;
        long bundleSize = buyQuantity + 1;
        long bundleCount = 10;
        ProductName productName = new ProductName("제품");
        Promotion promotion = new Promotion(null, buyQuantity, null, null);
        Stock stock = new Stock(normalQuantity, bundleSize * bundleCount);
        Product product = new Product(productName, price, stock, promotion);
        VerifiedOrder verifiedOrder = VerifiedOrder.of(product,
                normalQuantity + bundleSize * bundleCount);

        // then
        long result = verifiedOrder.totalDiscountByProduct();

        // when
        assertThat(result).isEqualTo(price * bundleCount);
    }

    @Test
    @DisplayName("모든 제품의 총 개수를 계산한다.")
    void test8() {
        long quantity = 10;
        long repeat = 100;
        VerifiedOrder verifiedOrder = new VerifiedOrder(null, quantity);
        List<VerifiedOrder> list = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            list.add(verifiedOrder);
        }

        long result = VerifiedOrder.getTotalCount(list);

        assertThat(result).isEqualTo(quantity * repeat);
    }


}
