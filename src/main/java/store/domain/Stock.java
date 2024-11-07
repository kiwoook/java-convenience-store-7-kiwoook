package store.domain;

import java.util.Objects;

public class Stock {
    private static final String COUNT_UNIT = "개";
    private static final String EMPTY_STOCK = "재고 없음";

    private long normalQuantity;
    private long promotionQuantity;

    public Stock(long normalQuantity, long promotionQuantity) {
        this.normalQuantity = normalQuantity;
        this.promotionQuantity = promotionQuantity;
    }

    public Stock() {
        this.normalQuantity = 0;
        this.promotionQuantity = 0;
    }

    public void addNormalProduct(long count) {
        this.normalQuantity += count;
    }

    public void addPromotionProduct(long count) {
        this.promotionQuantity += count;
    }

    public Long total() {
        return this.normalQuantity + this.promotionQuantity;
    }

    public String toPromotionCountString() {
        if (this.promotionQuantity == 0) {
            return EMPTY_STOCK;
        }
        return promotionQuantity + COUNT_UNIT;
    }

    public String toNormalCountString() {
        if (this.normalQuantity == 0) {
            return EMPTY_STOCK;
        }
        return normalQuantity + COUNT_UNIT;
    }

    public Long remainQuantity(Long requestQuantity, Promotion promotion) {
        validRequestQuantity(requestQuantity);
        if (promotion == null || promotionQuantity == 0) {
            return 0L;
        }

        if (promotionQuantity < requestQuantity) {
            return calculateNonPromotionQuantity(requestQuantity, promotion);
        }

        return promotion.getPromotionalGiftQuantity(requestQuantity);
    }

    private long calculateNonPromotionQuantity(Long requestQuantity, Promotion promotion) {
        long availablePromoUnits = Math.min(requestQuantity, promotionQuantity);

        long promotionBundle = availablePromoUnits / promotion.combinedQuantity();

        long availablePromotionQuantity =
                promotion.totalBuyQuantity(promotionBundle) + promotion.totalGetQuantity(promotionBundle);

        return availablePromotionQuantity - requestQuantity;
    }

    private void validRequestQuantity(Long requestQuantity) {
        if (requestQuantity > total()) {
            throw new IllegalStateException();
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
        Stock stock = (Stock) o;
        return normalQuantity == stock.normalQuantity && promotionQuantity == stock.promotionQuantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalQuantity, promotionQuantity);
    }
}
