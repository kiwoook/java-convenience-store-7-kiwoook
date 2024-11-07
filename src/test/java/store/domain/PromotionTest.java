package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Promotion promotion = new Promotion("1", 1, 1, LocalDate.now(), LocalDate.now());

        assertThat(promotion.isValidPromotion(LocalDate.now().minusDays(1))).isFalse();
    }

    @Test
    @DisplayName("마감 날짜 이후이면 false를 반환한다.")
    void test5() {
        Promotion promotion = new Promotion("1", 1, 1, LocalDate.now(), LocalDate.now());

        assertThat(promotion.isValidPromotion(LocalDate.now().plusDays(1))).isFalse();
    }
}
