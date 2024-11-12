package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.TAB;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import store.dto.OrderConfirmDto;
import store.enums.Confirmation;
import store.utils.StringUtils;

public class VerifiedOrder {

    private static final long ZERO = 0;

    private final Product product;
    private final long requestQuantity;

    protected VerifiedOrder(Product product, long requestQuantity) {
        this.product = product;
        this.requestQuantity = requestQuantity;
    }

    public static VerifiedOrder of(Product product, long requestQuantity) {
        return new VerifiedOrder(product, requestQuantity);
    }

    public static VerifiedOrder of(Product product, OrderConfirmDto orderConfirmDto, Confirmation confirmation) {
        return new VerifiedOrder(product, processQuantity(orderConfirmDto, confirmation));
    }

    public static long getTotalCount(List<VerifiedOrder> verifiedOrders) {
        return verifiedOrders.stream()
                .mapToLong(VerifiedOrder::getRequestQuantity)
                .sum();
    }

    private static long processQuantity(OrderConfirmDto confirmDto, Confirmation confirmation) {
        if (confirmDto.problemQuantity() > 0 && confirmation.equals(Confirmation.YES)) {
            return confirmDto.requestQuantity() + confirmDto.problemQuantity();
        }
        if (confirmDto.problemQuantity() < 0 && confirmation.equals(Confirmation.NO)) {
            return ZERO;
        }

        return confirmDto.requestQuantity();
    }

    public void applyStock() {
        product.applyStock(requestQuantity);
    }

    public String toQuantityString() {
        return String.valueOf(requestQuantity);
    }

    public String getStatus() {
        StringJoiner joiner = new StringJoiner(TAB);
        return joiner.add(product.toProductName())
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

        return joiner.add(product.toProductName() + TAB)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerifiedOrder that = (VerifiedOrder) o;
        return requestQuantity == that.requestQuantity && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, requestQuantity);
    }

    @Override
    public String toString() {
        return "OrderVerificationV2{" +
                "product=" + product +
                ", requestQuantity=" + requestQuantity +
                '}';
    }
}
