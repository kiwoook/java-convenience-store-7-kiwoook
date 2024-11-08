package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.exception.InvalidFormatException;

class PromotionTest {

    @Test
    @DisplayName("시작 기한이 오늘이면 true 반환.")
    void test1() {
        Promotion promotion = new Promotion("1", 1, 1, LocalDate.now(), LocalDate.now().plusDays(1));

        assertThat(promotion.isValidPromotion(LocalDate.now())).isTrue();
    }

    @Test
    @DisplayName("마감 기한이 오늘이면 true 반환.")
    void test2() {
        Promotion promotion = new Promotion("1", 1, 1, LocalDate.now().minusDays(1), LocalDate.now());

        assertThat(promotion.isValidPromotion(LocalDate.now())).isTrue();
    }

    @Test
    @DisplayName("시작과 마감 기한이 오늘이여도 true 반환")
    void test3() {
        Promotion promotion = new Promotion("1", 1, 1, LocalDate.now(), LocalDate.now());

        assertThat(promotion.isValidPromotion(LocalDate.now())).isTrue();
    }

    @Test
    @DisplayName("시작 날짜 전날이면 false를 반환한다.")
    void test4() {
        Promotion promotion = new Promotion("1", 1, 1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));

        assertThat(promotion.isValidPromotion(LocalDate.now().minusDays(1))).isFalse();
    }

    @Test
    @DisplayName("마감 날짜 이후이면 false를 반환한다.")
    void test5() {
        Promotion promotion = new Promotion("1", 1, 1, LocalDate.now().minusDays(2), LocalDate.now().minusDays(1));

        assertThat(promotion.isValidPromotion(LocalDate.now().plusDays(1))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("N+M일때 N만 사면 M을 반환한다.")
    @CsvSource(value = {"5:10", "1000000:200", "500:600", "1:1"}, delimiter = ':')
    void test6(String v1, String v2) {
        long n = Long.parseLong(v1);
        long m = Long.parseLong(v2);

        Promotion promotion = new Promotion("프로모션", n, m, null, LocalDate.now());
        long result = promotion.getPromotionalGiftQuantity(n);

        assertThat(result).isEqualTo(m);
    }

    @Test
    @DisplayName("프로모션 증정 조건이 0 있으면 에러를 반환한다.")
    void test7() {
        LocalDate now = LocalDate.now();

        assertThrows(InvalidFormatException.class, () ->
                new Promotion("프로모션", 0, 10, null, now));

        assertThrows(InvalidFormatException.class, () ->
                new Promotion("프로모션", 10, 0, null, now));
    }
}
