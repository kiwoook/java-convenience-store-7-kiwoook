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
    @CsvSource(value = {"101:-101", "99:0", "100:0"}, delimiter = ':')
    void test3(String value1, String value2) {
        long requestCnt1 = Long.parseLong(value1);
        long expect = Long.parseLong(value2);

        Promotion promotion = new Promotion(null, 1, 100, null, null);
        Stock stock = new Stock(100, 100);

        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("예시 검증")
    void test4(){

        Promotion promotion = new Promotion(null, 2, 1, null, null);
        Stock stock = new Stock(0, 6);
        long requestCnt1 = 2;
        long expect = 5;

        Long result = stock.remainQuantity(requestCnt1, promotion);

        assertThat(result).isEqualTo(expect);
    }
}