package store.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static store.enums.ErrorMessage.EXCEED_PURCHASE;
import static store.enums.ErrorMessage.INVALID_PURCHASE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.vo.ProductName;

class OrderInfoTest {

    @ParameterizedTest
    @DisplayName("입력값 검증 테스트")
    @ValueSource(strings = {"[콜라-10]", "[우오어어어어어ㅓ-10000000000000]", "[3-3]"})
    void test1(String input) {
        OrderInfo result = new OrderInfo(input);

        assertThat(result.toOriginalInput()).isEqualTo(input);
    }

    @ParameterizedTest
    @DisplayName("조건에 안맞는 입력은 예외 발생")
    @NullAndEmptySource
    @ValueSource(strings = {"[콜라-10-3]", "[-]", "[-3]",
            "[콜라-]", "[콜라-콜라]", "콜라-콜라",
            "콜라-3", "[ - ], [콜라--3]", "[-콜라-3-]",
            "[콜라-3-]", "[-콜라-3]", "[콜라-0]", "[콜라-10,사이다-3]"
    })
    void test2(String input) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderInfo(input);
        });

        assertThat(exception.getMessage()).isEqualTo(INVALID_PURCHASE.getMessage());
    }

    @Test
    @DisplayName("수량 존재")
    void test3() {
        ProductName productName = ProductName.create("제품");
        Stock stock = new Stock(10, 10);
        Product product = new Product(productName, 1000, stock, null);
        OrderInfo orderInfo = new OrderInfo("[제품-20]");

        orderInfo.validQuantity(product);
    }

    @Test
    @DisplayName("수량 초과 에러 반환")
    void test4() {
        ProductName productName = ProductName.create("제품");
        Stock stock = new Stock(10, 10);
        Product product = new Product(productName, 1000, stock, null);
        OrderInfo orderInfo = new OrderInfo("[제품-21]");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderInfo.validQuantity(product);
        });

        assertThat(exception.getMessage()).isEqualTo(EXCEED_PURCHASE.getMessage());
    }

}
