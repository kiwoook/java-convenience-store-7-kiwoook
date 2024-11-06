package store.dto;

import store.enums.PurchaseStatus;

/**
 * PROMOTION_ELIGIBLE -> problemCount 증정받을 수  있는 개수이다.
 * INSUFFICIENT_PROMOTION_QUANTITY -> problemCount 원가로 결제해야 할 개수이다.
 */

public class CheckPurchaseInfoDto {

    private String name;
    private Long requestCount;
    private Long problemCount;
    private PurchaseStatus purchaseStatus;
}
