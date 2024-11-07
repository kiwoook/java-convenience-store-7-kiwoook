package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.PurchaseConfirmDto;
import store.enums.Confirmation;

class PurchaseVerificationTest {

    @Test
    @DisplayName("프로모션 재고 부족으로 구매를 안한다.")
    void test1() {
        PurchaseVerification expected = new PurchaseVerification("제품", 0);

        PurchaseConfirmDto confirmDto = new PurchaseConfirmDto("제품", 10L, -5L);
        Confirmation no = Confirmation.NO;

        PurchaseVerification result = PurchaseVerification.of(confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("프로모션을 받는다.")
    void test2() {
        PurchaseVerification expected = new PurchaseVerification("제품", 15);

        PurchaseConfirmDto confirmDto = new PurchaseConfirmDto("제품", 10L, 5L);
        Confirmation no = Confirmation.YES;

        PurchaseVerification result = PurchaseVerification.of(confirmDto, no);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("상관없이 구매한다.")
    void test3() {
        PurchaseVerification expected = new PurchaseVerification("제품", 15);

        PurchaseConfirmDto confirmDto1 = new PurchaseConfirmDto("제품", 15L, 5L);
        PurchaseConfirmDto confirmDto2 = new PurchaseConfirmDto("제품", 15L, -5L);

        PurchaseVerification result1 = PurchaseVerification.of(confirmDto1, Confirmation.NO);
        PurchaseVerification result2 = PurchaseVerification.of(confirmDto2, Confirmation.YES);

        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }


}
