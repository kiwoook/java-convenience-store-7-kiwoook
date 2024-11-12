package store.domain;

import java.util.Objects;

public class Stock {
    private static final String COUNT_UNIT = "개";
    private static final String EMPTY_STOCK = "재고 없음";
    private static final long NOT_PROBLEM_COUNT = 0;

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

    public long calculateGiftQuantity(long requestQuantity, Promotion promotion) {
        if (promotion == null) {
            return 0L;
        }

        long promotionBundleCnt = getPromotionBundleCnt(requestQuantity, promotion);
        return promotion.totalGetQuantity(promotionBundleCnt);
    }

    public void applyQuantity(long requestQuantity) {
        long exceedPromotionQuantity = requestQuantity - promotionQuantity;

        if (exceedPromotionQuantity > 0) {
            updatePromotionProduct(0);
            updateNormalProduct(normalQuantity - exceedPromotionQuantity);
            return;
        }

        updatePromotionProduct(promotionQuantity - requestQuantity);
    }

    public void addNormalProduct(long count) {
        this.normalQuantity += count;
    }

    public void addPromotionProduct(long count) {
        this.promotionQuantity += count;
    }

    public long total() {
        return normalQuantity + promotionQuantity;
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

    /**
     * 프로모션에 따라 문제 있는 수량을 검증하는 메서드
     *
     * @param requestQuantity 요청 수량
     * @param promotion 프로모션 여부
     * @return 0보다 작으면 원가, 0보다 크면 증정하는 수량을 반환한다.
     */

    public long problemQuantity(long requestQuantity, Promotion promotion) {
        validRequestQuantity(requestQuantity);
        if (promotion == null || promotionQuantity == 0) {
            return NOT_PROBLEM_COUNT;
        }

        long giftQuantity = promotion.getPromotionGiftQuantity(requestQuantity);
        if (promotionQuantity < Math.max(requestQuantity + giftQuantity, promotion.bundleSize())) {
            return calculateOriginalPriceQuantity(requestQuantity, promotion);
        }

        return giftQuantity;
    }


    /**
     * 요청한 수량 중 원가로 처리되어야 하는 수량을 계산하는 메서드
     * 프로모션이 존재하지 않는 경우 원가로 되는 요청 수량을 음수로 반환
     *
     * @param requestQuantity 요청한 수량
     * @param promotion       프로모션
     * @return 원가로 처리되는 수량은 음수로 반환
     */

    public long calculateOriginalPriceQuantity(long requestQuantity, Promotion promotion) {
        validRequestQuantity(requestQuantity);
        if (promotion == null) {
            return -requestQuantity;
        }

        long promotionBundleCnt = getPromotionBundleCnt(requestQuantity, promotion);
        long totalPromotionQuantity = promotion.bundleSize() * promotionBundleCnt;

        return totalPromotionQuantity - requestQuantity;
    }

    /**
     * @param requestQuantity 요청한 수량
     * @param promotion 프로모션
     * @return 프로모션 묶음 수를 반환한다.
     */

    private long getPromotionBundleCnt(long requestQuantity, Promotion promotion) {
        long availablePromoUnits = Math.min(requestQuantity, promotionQuantity);

        return availablePromoUnits / promotion.bundleSize();
    }

    private void validRequestQuantity(long requestQuantity) {
        if (requestQuantity > total()) {
            throw new IllegalStateException("처리할 수 없는 요청 수량입니다!");
        }
    }

    private void updateNormalProduct(long count) {
        this.normalQuantity = count;
    }

    private void updatePromotionProduct(long count) {
        this.promotionQuantity = count;
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
