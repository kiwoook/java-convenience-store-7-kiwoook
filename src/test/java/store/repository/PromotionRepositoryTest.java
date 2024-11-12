package store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;
import store.repository.impl.PromotionRepositoryImpl;

class PromotionRepositoryTest {

    private final PromotionRepository promotionRepository = new PromotionRepositoryImpl();

    @Test
    @DisplayName("기존의 프로모션이 기한이 아니라면 기한 내 프로모션으로 교체")
    void test1() {
        String key = "promotion";
        LocalDate now = LocalDate.now();
        Promotion promotion = Promotion.create(key, 1, LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(1));
        promotionRepository.save(key, promotion, now);

        assertThat(promotionRepository.findById(key)).contains(promotion);

        Promotion newPromotion = Promotion.create(key, 1, now, now);
        promotionRepository.save(key, newPromotion, now);

        assertThat(promotionRepository.findById(key)).contains(newPromotion);
    }


    @AfterEach
    void cleanUp() {
        promotionRepository.clear();
    }
}
