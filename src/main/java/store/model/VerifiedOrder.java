package store.model;

import java.util.List;
import java.util.StringJoiner;
import store.utils.StringUtils;

public class VerifiedOrder {

    private static final String TAB = "\t";
    private static final String BLANK = " ";

    private final Product product;
    private final long requestQuantity;

    public VerifiedOrder(Product product, long requestQuantity) {
        this.product = product;
        this.requestQuantity = requestQuantity;
    }

    public static VerifiedOrder of(Product product, long requestQuantity) {
        return new VerifiedOrder(product, requestQuantity);
    }

    public static long getTotalCount(List<VerifiedOrder> verifiedOrders) {
        return verifiedOrders.stream()
                .mapToLong(VerifiedOrder::getRequestQuantity)
                .sum();
    }

    public void applyStock() {
        product.applyStock(requestQuantity);
    }

    public String toQuantityString() {
        return String.valueOf(requestQuantity);
    }

    public String getStatus() {
        StringJoiner joiner = new StringJoiner(TAB);
        return joiner.add(product.getName())
                .add(BLANK)
                .add(toQuantityString())
                .add(BLANK)
                .add(toFormatTotalPriceByProduct())
                .toString();
    }

    public String getDiscountStatus() {
        StringJoiner joiner = new StringJoiner(TAB);
        long giftQuantity = product.calculateGiftQuantity(requestQuantity);

        if (giftQuantity == 0) {
            return null;
        }

        return joiner.add(product.getName() + TAB)
                .add(String.valueOf(giftQuantity))
                .toString();
    }

    protected String toFormatTotalPriceByProduct() {
        long totalPrice = product.calculateSumPrice(requestQuantity);

        return StringUtils.numberFormat(totalPrice);
    }

    protected long getTotalOriginalPriceByProduct() {
        return product.sumOriginalPrice(requestQuantity);
    }

    protected long getTotalPrice() {
        return product.calculateSumPrice(requestQuantity);
    }

    protected long getTotalDiscountByProduct() {
        long giftQuantity = product.calculateGiftQuantity(requestQuantity);

        return product.calculateSumPrice(giftQuantity);
    }

    private long getRequestQuantity() {
        return requestQuantity;
    }
}
