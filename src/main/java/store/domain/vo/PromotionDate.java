package store.domain.vo;

import static store.enums.ErrorMessage.INVALID_INPUT;

import java.time.LocalDate;
import store.exception.InvalidFileFormatException;

public record PromotionDate(LocalDate startDate, LocalDate endDate) {
    private static final int ONE_DAY_OFFSET = 1;

    public PromotionDate {
        validPromotionDate(startDate, endDate);
    }

    public boolean isValidPromotion(LocalDate currentDate) {
        return currentDate.isAfter(startDate.minusDays(ONE_DAY_OFFSET)) && currentDate.isBefore(
                endDate.plusDays(ONE_DAY_OFFSET));
    }

    public void validPromotionDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isBefore(endDate)) {
            throw new InvalidFileFormatException(INVALID_INPUT.getMessage());
        }
    }
}
