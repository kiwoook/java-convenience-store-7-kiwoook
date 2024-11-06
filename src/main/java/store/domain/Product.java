package store.domain;

import static store.enums.ErrorMessage.EXCEED_PURCHASE;

public class Product {

    private String productName;
    private Stock stock;
    private Promotion promotion;
    // 가격이 동일하지 않다면 format 에러를 발생시킨다.

    // 요청받은 수량이 전체 재고를 넘어가면 에러를 발생시킨다.
    public void validStock(Long requestCount) {
        if (stock.total() < requestCount) {
            throw new IllegalArgumentException(EXCEED_PURCHASE.getMessage());
        }
    }

}
