package store.domain;

public class Stock {

    private long normalCount;
    private long promotionCount;

    public Stock() {
        this.normalCount = 0;
        this.promotionCount = 0;
    }

    public void addNormalProduct(long count) {
        this.normalCount += count;
    }

    public void addPromotionProduct(long count) {
        this.promotionCount += count;
    }

    public Long total() {
        return this.normalCount + this.promotionCount;
    }
}
