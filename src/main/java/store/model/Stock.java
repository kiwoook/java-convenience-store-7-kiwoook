package store.model;

import store.utils.ErrorMessage;

public class Stock {
    private static final String UNIT = "개";


    private long normalQuantity;
    private long promotionQuantity;

    public Stock() {
        this.normalQuantity = 0L;
        this.promotionQuantity = 0L;
    }

    public long checkProblemQuantity(Promotion promotion, long requestQuantity) {
        validRequestQuantity(requestQuantity);
        if (promotion == null || promotionQuantity == 0) {
            return 0;
        }

        long giftQuantity = promotion.getPromotionGiftQuantity(requestQuantity);
        if (promotionQuantity < Math.max(promotion.getBundleSize(), requestQuantity + giftQuantity)) {
            return calculateOriginalPriceQuantity(promotion, requestQuantity);
        }

        return giftQuantity;
    }

    public long calculateGiftQuantity(Promotion promotion, long requestQuantity) {
        if (promotion == null) {
            return 0;
        }
        return getPromotionBundleCnt(promotion, requestQuantity);
    }

    public long calculateOriginalPriceQuantity(Promotion promotion, long requestQuantity) {
        validRequestQuantity(requestQuantity);

        if (promotion == null) {
            return -requestQuantity;
        }

        long bundleCnt = getPromotionBundleCnt(promotion, requestQuantity);
        long availablePromotionQuantity = bundleCnt * promotion.getBundleSize();

        return availablePromotionQuantity - requestQuantity;
    }

    public long getPromotionBundleCnt(Promotion promotion, long requestQuantity) {
        return Math.min(promotionQuantity, requestQuantity) / promotion.getBundleSize();
    }

    public void validRequestQuantity(long requestQuantity) {
        if (requestQuantity > getTotalQuantity()) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_REQUEST_STOCK.getMessage());
        }
    }

    public long getTotalQuantity() {
        return normalQuantity + promotionQuantity;
    }

    public void addNormalQuantity(long normalQuantity) {
        this.normalQuantity += normalQuantity;
    }

    public void addPromotionQuantity(long promotionQuantity) {
        this.promotionQuantity += promotionQuantity;
    }

    public String toNormalQuantityString() {
        if (normalQuantity == 0) {
            return "재고 없음";
        }
        return normalQuantity + UNIT;
    }

    public String toPromotionQuantityString() {
        if (promotionQuantity == 0) {
            return "재고 없음";
        }
        return promotionQuantity + UNIT;
    }

    public void apply(long requestQuantity) {
        if (promotionQuantity < requestQuantity) {
            promotionQuantity = 0;
            normalQuantity = normalQuantity - (requestQuantity - promotionQuantity);
            return;
        }

        promotionQuantity -= requestQuantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "normalQuantity=" + normalQuantity +
                ", promotionQuantity=" + promotionQuantity +
                '}';
    }
}
