package store.enums;


public enum PurchaseStatus {

    /**
     * 프로모션 재고가 부족함을 알린다.
     */
    INSUFFICIENT_PROMOTION_QUANTITY,
    /**
     * 문제없이 구매 가능하다.
     */
    AVAILABLE_PURCHASE,
    /**
     * 프로모션 수량을 받을 수 있다.
     */
    PROMOTION_ELIGIBLE

}
