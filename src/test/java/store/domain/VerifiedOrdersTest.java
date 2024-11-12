package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.vo.ProductName;

class VerifiedOrdersTest {

    @Test
    @DisplayName("제품들의 총 금액과 총 개수를 반환한다.")
    void test1() {
        // given
        int repeat = 10;
        long price = 1000;
        long quantity = 10;

        ProductName productName = new ProductName("testProduct");
        Stock stock = new Stock(quantity, 0);
        Product product = new Product(productName, price, stock, null);
        List<VerifiedOrder> list = new ArrayList<>();

        for (int i = 0; i < repeat; i++) {
            list.add(new VerifiedOrder(product, quantity));
        }
        VerifiedOrders verifiedOrders = new VerifiedOrders(list);

        // then
        long resultPrice = verifiedOrders.getTotalPrice();
        long totalCount = verifiedOrders.getTotalCount();

        // when
        long expectedPrice = repeat * quantity * price;
        long expectedCount = repeat * quantity;
        assertThat(resultPrice).isEqualTo(expectedPrice);
        assertThat(totalCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("총 할인된 금액을 반환한다.")
    void test2() {
        int repeat = 10;
        long price = 1000;

        ProductName productName = new ProductName("testProduct");
        Stock stock = new Stock(0, 2);
        Promotion promotion = new Promotion("testPromotion", 1, LocalDate.now(), LocalDate.now());
        Product product = new Product(productName, price, stock, promotion);
        List<VerifiedOrder> list = new ArrayList<>();

        for (int i = 0; i < repeat; i++) {
            list.add(new VerifiedOrder(product, 2));
        }
        VerifiedOrders verifiedOrders = new VerifiedOrders(list);

        long result = verifiedOrders.getTotalDiscount();
        long expected = price * repeat;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("재고가 부족하면 할인되지 않는다.")
    void test3() {
        int repeat = 10;
        long price = 1000;

        ProductName productName = new ProductName("testProduct");
        Stock stock = new Stock(1, 1);
        Promotion promotion = new Promotion("testPromotion", 1, LocalDate.now(), LocalDate.now());
        Product product = new Product(productName, price, stock, promotion);
        List<VerifiedOrder> list = new ArrayList<>();

        for (int i = 0; i < repeat; i++) {
            list.add(new VerifiedOrder(product, 2));
        }
        VerifiedOrders verifiedOrders = new VerifiedOrders(list);

        long result = verifiedOrders.getTotalDiscount();
        assertThat(result).isZero();
    }

    @Test
    @DisplayName("프로모션 재고 요건이 안되면 모두 원금으로 반환된다.")
    void test4() {
        int repeat = 10;
        long price = 1000;

        ProductName productName = new ProductName("testProduct");
        Stock stock = new Stock(1, 1);
        Promotion promotion = new Promotion("testPromotion", 1, LocalDate.now(), LocalDate.now());
        Product product = new Product(productName, price, stock, promotion);
        List<VerifiedOrder> list = new ArrayList<>();

        for (int i = 0; i < repeat; i++) {
            list.add(new VerifiedOrder(product, 2));
        }
        VerifiedOrders verifiedOrders = new VerifiedOrders(list);

        long result = verifiedOrders.getTotalOriginalPrice();
        long expected = repeat * price * 2;
        assertThat(result).isEqualTo(expected);
    }

}
