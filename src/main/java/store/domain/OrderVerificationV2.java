package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.TAB;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import store.dto.OrderConfirmDto;
import store.enums.Confirmation;
import store.utils.StringUtils;

public class OrderVerificationV2 {

    private static final long ZERO = 0;

    private final Product product;
    private final long requestQuantity;

    protected OrderVerificationV2(Product product, long requestQuantity) {
        this.product = product;
        this.requestQuantity = requestQuantity;
    }

    public static OrderVerificationV2 of(Product product, long requestQuantity) {
        return new OrderVerificationV2(product, requestQuantity);
    }

    public static OrderVerificationV2 of(Product product, OrderConfirmDto orderConfirmDto, Confirmation confirmation) {
        return new OrderVerificationV2(product, processQuantity(orderConfirmDto, confirmation));
    }

    public static long processQuantity(OrderConfirmDto confirmDto, Confirmation confirmation) {
        if (confirmDto.problemQuantity() > 0 && confirmation.equals(Confirmation.YES)) {
            return confirmDto.requestQuantity() + confirmDto.problemQuantity();
        }
        if (confirmDto.problemQuantity() < 0 && confirmation.equals(Confirmation.NO)) {
            return ZERO;
        }

        return confirmDto.requestQuantity();
    }

    public static long getTotalCount(List<OrderVerificationV2> orderVerificationV2s) {
        return orderVerificationV2s.stream()
                .mapToLong(OrderVerificationV2::getRequestQuantity)
                .sum();
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

    protected long totalDiscountByProduct() {
        long giftQuantity = product.calculateGiftQuantity(requestQuantity);

        return product.calculateSumPrice(giftQuantity);
    }

    public void applyStock() {
        product.applyStock(requestQuantity);
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
        OrderVerificationV2 that = (OrderVerificationV2) o;
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
