package store.domain;

import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;

import java.time.LocalDate;
import java.util.Objects;
import store.domain.vo.PromotionBundle;
import store.domain.vo.PromotionDate;
import store.exception.InvalidFormatException;

public class Promotion {

    private final String name;
    private final PromotionDate promotionDate;
    private final PromotionBundle promotionBundle;

    public Promotion(String name, long buyQuantity, long getQuantity, LocalDate startDate, LocalDate endDate) {
        validPromotionQuantity(buyQuantity, getQuantity);
        this.name = name;
        this.promotionDate = new PromotionDate(startDate, endDate);
        this.promotionBundle = new PromotionBundle(buyQuantity, getQuantity);
    }

    public static Promotion create(String name, long buy, long get, LocalDate startDate, LocalDate endDate) {
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public boolean isValidPromotion(LocalDate currentDate) {
        return promotionDate.isValidPromotion(currentDate);
    }

    protected long bundleSize() {
        return promotionBundle.getQuantity() + promotionBundle.buyQuantity();
    }

    protected long totalGetQuantity(long promotionBundleCount) {
        return promotionBundleCount * promotionBundle.getQuantity();
    }

    protected long getPromotionGiftQuantity(long requestQuantity) {
        if ((requestQuantity + promotionBundle.getQuantity()) % bundleSize() == 0) {
            return promotionBundle.getQuantity();
        }
        return 0L;
    }

    private void validPromotionQuantity(Long buyQuantity, Long getQuantity) {
        if (buyQuantity == 0 || getQuantity == 0) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotion promotion = (Promotion) o;
        return Objects.equals(name, promotion.name) && Objects.equals(promotionDate,
                promotion.promotionDate) && Objects.equals(promotionBundle, promotion.promotionBundle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, promotionDate, promotionBundle);
    }

    @Override
    public String toString() {
        return name;
    }
}
