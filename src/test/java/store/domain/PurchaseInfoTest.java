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

class PurchaseInfoTest {

    @ParameterizedTest
    @DisplayName("입력값 검증 테스트")
    @ValueSource(strings = {"[콜라-10]", "[우오어어어어어ㅓ-10000000000000]", "[3-3]"})
    void test1(String input) {
        PurchaseInfo result = new PurchaseInfo(input);

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
            new PurchaseInfo(input);
        });

        assertThat(exception.getMessage()).isEqualTo(INVALID_PURCHASE.getMessage());
    }

    @Test
    @DisplayName("수량 존재")
    void test3() {
        Stock stock = new Stock(10, 10);
        Product product = new Product("제품", 1000, stock, null);
        PurchaseInfo purchaseInfo = new PurchaseInfo("[제품-20]");

        purchaseInfo.validQuantity(product);
    }

    @Test
    @DisplayName("수량 초과 에러 반환")
    void test4() {
        Stock stock = new Stock(10, 10);
        Product product = new Product("제품", 1000, stock, null);
        PurchaseInfo purchaseInfo = new PurchaseInfo("[제품-21]");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            purchaseInfo.validQuantity(product);
        });

        assertThat(exception.getMessage()).isEqualTo(EXCEED_PURCHASE.getMessage());
    }

}
