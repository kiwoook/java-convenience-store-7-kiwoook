package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.vo.ProductName;
import store.dto.OrderConfirmDto;
import store.enums.Confirmation;

class OrderVerificationTest {

    @Test
    @DisplayName("프로모션 재고 부족으로 구매를 안한다.")
    void test1() {
        ProductName productName = new ProductName("제품");
        Stock stock = new Stock(15, 10);
        Product product = new Product(productName, 1000, stock, null);
        OrderVerificationV2 expected = new OrderVerificationV2(product, 0);

        OrderConfirmDto confirmDto = new OrderConfirmDto(productName, 10L, -5L);
        Confirmation no = Confirmation.NO;

        OrderVerificationV2 result = OrderVerificationV2.of(product, confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("프로모션을 받는다.")
    void test2() {
        ProductName productName = new ProductName("제품");
        Stock stock = new Stock(0, 15);
        Product product = new Product(productName, 1000, stock, null);
        OrderVerificationV2 expected = new OrderVerificationV2(product, 15);

        OrderConfirmDto confirmDto = new OrderConfirmDto(productName, 10L, 5L);
        Confirmation no = Confirmation.YES;

        OrderVerificationV2 result = OrderVerificationV2.of(product, confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("상관없이 구매한다.")
    void test3() {
        ProductName productName = new ProductName("제품");
        Stock stock = new Stock(15, 15);
        Product product = new Product(productName, 1000, stock, null);

        OrderVerificationV2 expected = new OrderVerificationV2(product, 15);

        OrderConfirmDto confirmDto1 = new OrderConfirmDto(productName, 15L, 5L);
        OrderConfirmDto confirmDto2 = new OrderConfirmDto(productName, 15L, -5L);

        OrderVerificationV2 result1 = OrderVerificationV2.of(product, confirmDto1, Confirmation.NO);
        OrderVerificationV2 result2 = OrderVerificationV2.of(product, confirmDto2, Confirmation.YES);

        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }


}
