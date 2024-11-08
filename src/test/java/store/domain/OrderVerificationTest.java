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
        OrderVerification expected = new OrderVerification(productName, 0);

        OrderConfirmDto confirmDto = new OrderConfirmDto(productName, 10L, -5L);
        Confirmation no = Confirmation.NO;

        OrderVerification result = OrderVerification.of(confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("프로모션을 받는다.")
    void test2() {
        ProductName productName = new ProductName("제품");

        OrderVerification expected = new OrderVerification(productName, 15);

        OrderConfirmDto confirmDto = new OrderConfirmDto(productName, 10L, 5L);
        Confirmation no = Confirmation.YES;

        OrderVerification result = OrderVerification.of(confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("상관없이 구매한다.")
    void test3() {
        ProductName productName = new ProductName("제품");

        OrderVerification expected = new OrderVerification(productName, 15);

        OrderConfirmDto confirmDto1 = new OrderConfirmDto(productName, 15L, 5L);
        OrderConfirmDto confirmDto2 = new OrderConfirmDto(productName, 15L, -5L);

        OrderVerification result1 = OrderVerification.of(confirmDto1, Confirmation.NO);
        OrderVerification result2 = OrderVerification.of(confirmDto2, Confirmation.YES);

        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }


}
