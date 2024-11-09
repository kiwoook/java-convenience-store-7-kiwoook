package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
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
    @ValueSource(longs = {26667, 100000000, 1000000, 1000000000000L})
    void test2(Long value) {
        CalculatePrice calculatePrice = new CalculatePrice(0, 0, 0, value);
        long result = calculatePrice.calculateMembershipDiscount(Confirmation.YES);

        assertThat(result).isEqualTo(8000);
    }

    @Test
    @DisplayName("프로모션 재고가 충분하고 번들만큼 사면 멤버십 할인은 되지 않는다.")
    void test3() {
        for (int n = 1; n <= 1000; n++) {
            for (int m = 1; m <= 1000; m++) {
                Promotion promotion = new Promotion(null, n, m, null, null);
                Stock stock = new Stock(0, n + m);
                Product product = new Product(null, 1000, stock, promotion);
                OrderVerification orderVerification = new OrderVerification(null, n + m);

                CalculatePrice calculatePrice = CalculatePrice.create();
                calculatePrice.calculate(orderVerification, product);
                long result = calculatePrice.calculateMembershipDiscount(Confirmation.YES);

                assertThat(result).isZero();
            }
        }
    }

    @ParameterizedTest
    @DisplayName("개수, 총금액, 원가 검증 테스트")
    @ValueSource(ints = {10, 100, 100000, 10000000, 555})
    void test4(int size) {
        long price = 1000L;
        List<Product> products = new ArrayList<>();
        List<OrderVerification> verifications = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Stock stock = new Stock(1, 0);
            Product product = new Product(null, price, stock, null);
            OrderVerification verification = new OrderVerification(null, 1);

            products.add(product);
            verifications.add(verification);
        }

        CalculatePrice result = CalculatePrice.create();

        for (int i = 0; i < size; i++) {
            Product product = products.get(i);
            OrderVerification orderVerification = verifications.get(i);

            result.calculate(orderVerification, product);
        }

        CalculatePrice expect = new CalculatePrice(size, size * price, 0, size * price);
        assertThat(result).isEqualTo(expect);
    }

}
