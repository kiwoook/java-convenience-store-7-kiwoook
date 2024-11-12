package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static store.enums.ErrorMessage.INVALID_PURCHASE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderInfosTest {

    @ParameterizedTest
    @DisplayName("잘못된 입력을 하면 에러를 발생한다.")
    @NullAndEmptySource
    @ValueSource(strings = {"[콜라-10][사이다-3]", "[-10],[사이다-3]", "[콜라-10],[사이다-3],",
            "[콜라-10],[사이다-3],", "콜라-10,사이다-3", "[콜라-10,사이다-3]",
            "[콜라,사이다-3]", "[콜라-10], 사이다-3", "[콜라-10], [사이다-3]",
            "[]", "[-]", "[-],[-]", "-", "[,,,,,,]","[콜라-10],,[사이다-3]", ",[콜라-10],[사이다-3]"
    })
    void test1(String input) {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            OrderInfos.create(input);
        });

        assertThat(exception.getMessage()).isEqualTo(INVALID_PURCHASE.getMessage());
    }

    @ParameterizedTest
    @DisplayName("정상적인 입력")
    @CsvSource(
            value = {"[콜라-10]:1", "[콜라-10],[사이다-3]:2", "[물-1]:1"},
            delimiter = ':'
    )
    void test2(String input, String result) {
        OrderInfos purchaseInfos = OrderInfos.create(input);

        assertThat(purchaseInfos.size()).isEqualTo(Integer.parseInt(result));
    }

    @ParameterizedTest
    @DisplayName("중복된 제품은 병합되어서 반환된다.")
    @ValueSource(strings = {"[콜라-10],[콜라-20],[사이다-10]"})
    void test2(String input) {
        OrderInfo orderInfo1 = OrderInfo.create("[사이다-10]");
        OrderInfo orderInfo2 = OrderInfo.create("[콜라-30]");
        OrderInfos expect = new OrderInfos();
        expect.addPurchaseInfo(orderInfo2);
        expect.addPurchaseInfo(orderInfo1);

        OrderInfos result = OrderInfos.create(input);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(expect);
    }

}
