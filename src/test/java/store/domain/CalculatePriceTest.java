package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.enums.Confirmation;

class CalculatePriceTest {

    @ParameterizedTest
    @DisplayName("멤버십 할인을 하면 30% 할인을 받을 수 있다.")
    @ValueSource(longs = {10000, 10, 26666, 1, 0})
    void test1(Long value) {

        CalculatePrice calculatePrice = new CalculatePrice(0, 0, 0, value);
        long result = calculatePrice.calculateMembershipDiscount(Confirmation.YES);

        assertThat(result).isEqualTo((value * 3) / 10);
    }

    @ParameterizedTest
    @DisplayName("멤버십 할인의 한도는 8000원까지이다.")
    @ValueSource(longs = {26667,100000000,1000000,1000000000000L})
    void test2(Long value) {
        CalculatePrice calculatePrice = new CalculatePrice(0, 0, 0, value);
        long result = calculatePrice.calculateMembershipDiscount(Confirmation.YES);

        assertThat(result).isEqualTo(8000);
    }

    @Test
    @DisplayName("1+1이고 프로모션 재고가 존재할 때")
    void test1(){
        Promotion promotion = new Promotion("1+1", 1, 1, null, null);
        Stock stock = new Stock(0,2);

    }
}
