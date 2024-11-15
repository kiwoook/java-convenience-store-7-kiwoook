package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StockTest {

    @ParameterizedTest
    @DisplayName("프로모션 재고 존재, 2+1에 대한 검증 테스트")
    @CsvSource(value = {"2:1", "5:1", "4:0"}, delimiter = ':')
    void test1(String value1, String value2) {
        // given
        long requestCnt1 = Long.parseLong(value1);
        long expect = Long.parseLong(value2);
        Promotion promotion = new Promotion(null, 2, null, null);
        Stock stock = new Stock(0, 6);

        // when
        Long result = stock.problemQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @ParameterizedTest
    @DisplayName("프로모션 재고 부족, 2+1에 대한 검증")
    @CsvSource(value = {"6:-3"}, delimiter = ':')
    void test2(String value1, String value2) {
        long requestCnt1 = Long.parseLong(value1);
        long expect = Long.parseLong(value2);

        Promotion promotion = new Promotion(null, 2, null, null);
        Stock stock = new Stock(1, 5);

        Long result = stock.problemQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @ParameterizedTest
    @DisplayName("1+1 검증")
    @CsvSource(value = {"101:-101", "99:-99", "100:-100", "1:-1"}, delimiter = ':')
    void test3(String value1, String value2) {
        long requestCnt1 = Long.parseLong(value1);
        long expect = Long.parseLong(value2);

        Promotion promotion = new Promotion(null, 1, null, null);
        Stock stock = new Stock(100, 1);

        Long result = stock.problemQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증: 2+1")
    void test4() {
        Promotion promotion = new Promotion(null, 2, null, null);
        Stock stock = new Stock(0, 6);
        long requestCnt1 = 2;
        long expect = 1;

        Long result = stock.problemQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증 : 오렌지주스 1+1")
    void test5() {
        Promotion promotion = new Promotion(null, 1, null, null);
        Stock stock = new Stock(0, 9);
        long requestCnt1 = 1;
        long expect = 1;

        Long result = stock.problemQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증 : 콜라2+1")
    void test6() {
        Promotion promotion = new Promotion(null, 2, null, null);
        Stock stock = new Stock(10, 7);
        long requestCnt1 = 10;
        long expect = -4;

        Long result = stock.problemQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증 오렌지 주스")
    void test7() {
        Promotion promotion = new Promotion(null, 1, null, null);
        Stock stock = new Stock(0, 5);

        long result = stock.calculateOriginalPriceQuantity(2, promotion);

        assertThat(result).isZero();
    }

    @Test
    @DisplayName("원가 반환 테스트")
    void test8() {
        Promotion promotion = new Promotion(null, 1, null, null);
        Stock stock = new Stock(10, 1);

        for (int expected = 1; expected <= 11; expected++) {
            long result = stock.calculateOriginalPriceQuantity(expected, promotion);

            assertThat(result).isEqualTo(-expected);
        }
    }

    @Test
    @DisplayName("프로모션이 없다면 요청 개수는 원가 개수이다")
    void test9() {
        long maxExpected = 1000000;
        Stock stock = new Stock(maxExpected, 0);

        for (long expected = 1; expected < maxExpected; expected++) {
            long result = -stock.calculateOriginalPriceQuantity(expected, null);

            assertThat(result).isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("2+1이고 재고가 5개일 때 5개를 요청하면 -2를 반환해야 한다.")
    void test10() {
        Promotion promotion = new Promotion(null, 2, null, null);
        Stock stock = new Stock(0, 5);

        long result = stock.problemQuantity(5, promotion);

        assertThat(result).isEqualTo(-2);
    }

    @Test
    @DisplayName("재고에 여유 프로모션 재고가 부족하다면 요청 수량 반환")
    void test11() {
        long maxNormalQuantity = 1000000L;
        Promotion promotion = new Promotion(null, 1, null, null);
        Stock stock = new Stock(maxNormalQuantity, 1);

        for (long requestQuantity = 1; requestQuantity < maxNormalQuantity; requestQuantity++) {
            long result = stock.problemQuantity(requestQuantity, promotion);
            assertThat(-result).isEqualTo(requestQuantity);
        }
    }

    @Test
    @DisplayName("요청 재고를 적용할 때 프로모션 재고부터 나가야 한다.")
    void test12() {
        Stock result = new Stock(2, 10);
        Stock expected = new Stock(1, 0);

        result.applyQuantity(11);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("재고보다 많은 요청은 에러를 발생한다.")
    void test13() {
        long quantity = 10;
        Stock stock = new Stock(quantity, quantity);
        assertThrows(IllegalStateException.class, () -> {
            stock.problemQuantity(quantity * 2 + 1, null);
        });
    }
}
