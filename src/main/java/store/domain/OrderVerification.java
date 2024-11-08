package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.TAB;

import java.util.Objects;
import java.util.StringJoiner;
import store.dto.OrderConfirmDto;
import store.enums.Confirmation;
import store.utils.StringFormat;

public class OrderVerification {

    private static final long ZERO = 0;

    private final String name;
    private final long requestQuantity;

    public OrderVerification(String name, long requestQuantity) {
        this.name = name;
        this.requestQuantity = requestQuantity;
    }

    public static OrderVerification from(OrderConfirmDto confirmDto) {
        return new OrderVerification(confirmDto.name(), confirmDto.requestQuantity());
    }

    public static OrderVerification of(OrderConfirmDto confirmDto, Confirmation confirmation) {
        return new OrderVerification(confirmDto.name(), processQuantity(confirmDto, confirmation));
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

    public long getGiftQuantity(Product product) {
        return product.calculateGiftQuantity(this.requestQuantity);
    }

    public String totalPriceByProduct(Product product) {
        long totalPrice = product.calculateSumPrice(this.requestQuantity);
        return StringFormat.number(totalPrice);
    }

    public long getRequestQuantity() {
        return this.requestQuantity;
    }

    public String toQuantityString() {
        return String.valueOf(requestQuantity);
    }


    public String getStatus(Product product) {
        StringJoiner joiner = new StringJoiner(TAB);

        return joiner.add(this.name)
                .add(BLANK)
                .add(this.toQuantityString())
                .add(BLANK)
                .add(totalPriceByProduct(product))
                .toString();
    }

    public String getDiscountStatus(Product product) {
        StringJoiner joiner = new StringJoiner(TAB);
        long giftQuantity = product.calculateGiftQuantity(this.requestQuantity);

        if (giftQuantity == 0) {
            return null;
        }

        return joiner.add(this.name)
                .add(BLANK)
                .add(String.valueOf(giftQuantity))
                .toString();
    }

    public long totalOriginalPrice(Product product) {
        return product.sumOriginalPrice(this.requestQuantity);
    }

    public long totalPrice(Product product) {
        return product.calculateSumPrice(this.requestQuantity);
    }

    public long totalDiscount(Product product) {
        long giftQuantity = product.calculateGiftQuantity(this.requestQuantity);

        return product.calculateSumPrice(giftQuantity);
    }

    public void apply(Product product) {
        product.applyStock(this.requestQuantity);
    }

    @Override

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderVerification that = (OrderVerification) o;
        return requestQuantity == that.requestQuantity && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, requestQuantity);
    }
}
