package store.model;

public class Promotion {

    public static final int GET_QUANTITY = 1;

    private final String name;
    private final long buyQuantity;

    public Promotion(String name, int buyQuantity) {
        this.name = name;
        this.buyQuantity = buyQuantity;
    }

    // 여기서 시간 끌림
    public long getPromotionGiftQuantity(long requestQuantity) {
        if ((requestQuantity + GET_QUANTITY) % getBundleSize() == 0) {
            return GET_QUANTITY;
        }

        return 0;
    }

    public long getBundleSize() {
        return buyQuantity + GET_QUANTITY;
    }

    public String getName() {
        return name;
    }
}
