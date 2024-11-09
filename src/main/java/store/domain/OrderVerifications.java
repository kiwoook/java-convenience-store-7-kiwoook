package store.domain;

import static store.utils.Constants.ENTER;

import java.util.List;
import java.util.StringJoiner;

public class OrderVerifications {

    private final List<OrderVerificationV2> items;

    public OrderVerifications(List<OrderVerificationV2> items) {
        this.items = items;
    }

    public long getTotalCount() {
        return OrderVerificationV2.getTotalCount(items);
    }

    public long getTotalPrice() {
        return items.stream()
                .mapToLong(OrderVerificationV2::totalPrice)
                .sum();
    }

    public long getTotalOriginalPrice() {
        return items.stream()
                .mapToLong(OrderVerificationV2::totalOriginalPriceByProduct)
                .sum();
    }

    public long getTotalDiscount() {
        return items.stream()
                .mapToLong(OrderVerificationV2::totalDiscountByProduct)
                .sum();
    }

    public String getOrderStatus() {
        StringJoiner joiner = new StringJoiner(ENTER);

        for (OrderVerificationV2 orderVerification : items) {
            joiner.add(orderVerification.getStatus());
        }

        return joiner.toString();
    }

    public String getDiscountStatus() {
        StringJoiner joiner = new StringJoiner(ENTER);

        for (OrderVerificationV2 orderVerification : items) {
            String discountStatus = orderVerification.getDiscountStatus();
            if (discountStatus != null) {
                joiner.add(discountStatus);
            }
        }

        return joiner.toString();
    }

    public void apply() {
        items.forEach(OrderVerificationV2::apply);
    }


}
