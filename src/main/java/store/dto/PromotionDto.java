package store.dto;

import java.time.LocalDate;
import store.domain.Promotion;

public record PromotionDto(String name, long buy, LocalDate startDate, LocalDate endDate) {

    public Promotion toPromotion() {
        return Promotion.create(name, buy, startDate, endDate);
    }
}
