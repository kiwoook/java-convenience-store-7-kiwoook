package store.domain;

public class PurchaseInfo {
    private String productName;
    private Long requestCount;


    private void validPurchase(Product product) {
        product.validStock(this.requestCount);
    }

    // 프로모션 연산을 한 후 부족하거나 증정할 수 있는 제품이라면 추가하거나 빼내야한다.
    // 예외로 탈출하기는 어렵다.
    // 한 번에 모아둔 후

}
