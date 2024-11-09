package store.domain;

import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;

import java.time.LocalDate;
import java.util.Objects;
import store.domain.vo.PromotionDate;
import store.exception.InvalidFileFormatException;

public class Promotion {

    private static final long GET_PROMOTION_QUANTITY = 1;

    private final String name;
    private final PromotionDate promotionDate;
    private final long buyPromotionQuantity;

    public Promotion(String name, long buyQuantity, LocalDate startDate, LocalDate endDate) {
        validPromotionQuantity(buyQuantity);
        this.name = name;
        this.promotionDate = new PromotionDate(startDate, endDate);
        this.buyPromotionQuantity = buyQuantity;
    }

    public static Promotion create(String name, long buy, LocalDate startDate, LocalDate endDate) {
        return new Promotion(name, buy, startDate, endDate);
    }

    public boolean isValidPromotion(LocalDate currentDate) {
        return promotionDate.isValidPromotion(currentDate);
    }

    protected long bundleSize() {
        return GET_PROMOTION_QUANTITY + buyPromotionQuantity;
    }

    protected long totalGetQuantity(long promotionBundleCount) {
        return promotionBundleCount * GET_PROMOTION_QUANTITY;
    }

    protected long getPromotionGiftQuantity(long requestQuantity) {
        if ((requestQuantity + GET_PROMOTION_QUANTITY) % bundleSize() == 0) {
            return GET_PROMOTION_QUANTITY;
        }
        return 0L;
    }

    private void validPromotionQuantity(Long buyQuantity) {
        if (buyQuantity <= 0) {
            throw new InvalidFileFormatException(INVALID_FILE_FORMAT.getMessage());
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
        return buyPromotionQuantity == promotion.buyPromotionQuantity && Objects.equals(name, promotion.name)
                && Objects.equals(promotionDate, promotion.promotionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, promotionDate, buyPromotionQuantity);
    }

    @Override
    public String toString() {
        return name;
    }
}
