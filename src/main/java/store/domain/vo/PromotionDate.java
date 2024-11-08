package store.domain.vo;

import java.time.LocalDate;

public record PromotionDate(LocalDate startDate, LocalDate endDate) {
    private static final int ONE_DAY_OFFSET = 1;

    public boolean isValidPromotion(LocalDate currentDate) {
        return currentDate.isAfter(startDate.minusDays(ONE_DAY_OFFSET)) && currentDate.isBefore(
                endDate.plusDays(ONE_DAY_OFFSET));
    }
}
