package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
        Promotion promotion = new Promotion(null, 2, 1, null, null);
        Stock stock = new Stock(0, 6);

        // when
        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @ParameterizedTest
    @DisplayName("프로모션 재고 부족, 2+1에 대한 검증")
    @CsvSource(value = {"6:-3"}, delimiter = ':')
    void test2(String value1, String value2) {
        long requestCnt1 = Long.parseLong(value1);
        long expect = Long.parseLong(value2);

        Promotion promotion = new Promotion(null, 2, 1, null, null);
        Stock stock = new Stock(1, 5);

        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @ParameterizedTest
    @DisplayName("1+100 검증")
    @CsvSource(value = {"101:-101", "99:-99", "100:-100", "1:-1"}, delimiter = ':')
    void test3(String value1, String value2) {
        long requestCnt1 = Long.parseLong(value1);
        long expect = Long.parseLong(value2);

        Promotion promotion = new Promotion(null, 1, 100, null, null);
        Stock stock = new Stock(100, 100);

        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증: 2+1")
    void test4() {
        Promotion promotion = new Promotion(null, 2, 1, null, null);
        Stock stock = new Stock(0, 6);
        long requestCnt1 = 2;
        long expect = 1;

        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증 : 오렌지주스 1+1")
    void test5() {
        Promotion promotion = new Promotion(null, 1, 1, null, null);
        Stock stock = new Stock(0, 9);
        long requestCnt1 = 1;
        long expect = 1;

        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증 : 콜라2+1")
    void test6() {
        Promotion promotion = new Promotion(null, 2, 1, null, null);
        Stock stock = new Stock(10, 7);
        long requestCnt1 = 10;
        long expect = -4;

        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증 오렌지 주스")
    void test7() {
        Promotion promotion = new Promotion(null, 1, 1, null, null);
        Stock stock = new Stock(0, 5);

        long result = stock.calculateOriginalPriceQuantity(2, promotion);

        assertThat(result).isZero();
    }

    @Test
    @DisplayName("원가 반환 테스트")
    void test8() {
        Promotion promotion = new Promotion(null, 1, 1, null, null);
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
        Promotion promotion = new Promotion(null, 2, 1, null, null);
        Stock stock = new Stock(0, 5);

        long result = stock.remainQuantity(5, promotion);

        assertThat(result).isEqualTo(-2);
    }


}
