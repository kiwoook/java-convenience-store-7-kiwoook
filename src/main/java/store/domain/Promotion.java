package store.domain;

import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;
import static store.enums.ErrorMessage.INVALID_INPUT;

import java.time.LocalDate;
import java.util.Objects;
import store.domain.vo.PromotionDate;
import store.exception.InvalidFileFormatException;
import store.utils.StringUtils;

public class Promotion {

    private static final String NULL_NAME = "null";
    private static final long GET_PROMOTION_QUANTITY = 1;

    private final String name;
    private final PromotionDate promotionDate;
    private final long buyPromotionQuantity;

    protected Promotion(String name, long buyQuantity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.promotionDate = new PromotionDate(startDate, endDate);
        this.buyPromotionQuantity = buyQuantity;
    }

    public static Promotion create(String name, long buyQuantity, LocalDate startDate, LocalDate endDate) {
        StringUtils.validName(name);
        validPromotionName(name);
        validPromotionQuantity(buyQuantity);
        return new Promotion(name, buyQuantity, startDate, endDate);
    }

    private static void validPromotionQuantity(Long buyQuantity) {
        if (buyQuantity <= 0) {
            throw new InvalidFileFormatException(INVALID_FILE_FORMAT.getMessage());
        }
    }

    private static void validPromotionName(String name) {
        if (name.equalsIgnoreCase(NULL_NAME)) {
            throw new InvalidFileFormatException(INVALID_INPUT.getMessage());
        }
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
