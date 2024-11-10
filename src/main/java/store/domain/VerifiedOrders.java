package store.domain;

import static store.utils.Constants.ENTER;

import java.util.List;
import java.util.StringJoiner;

public class VerifiedOrders {

    private final List<VerifiedOrder> items;

    public VerifiedOrders(List<VerifiedOrder> items) {
        this.items = items;
    }

    public long getTotalCount() {
        return VerifiedOrder.getTotalCount(items);
    }

    public long getTotalPrice() {
        return items.stream()
                .mapToLong(VerifiedOrder::getTotalPrice)
                .sum();
    }

    public long getTotalOriginalPrice() {
        return items.stream()
                .mapToLong(VerifiedOrder::getTotalOriginalPriceByProduct)
                .sum();
    }

    public long getTotalDiscount() {
        return items.stream()
                .mapToLong(VerifiedOrder::totalDiscountByProduct)
                .sum();
    }

    public String getOrderStatus() {
        StringJoiner joiner = new StringJoiner(ENTER);

        for (VerifiedOrder verifiedOrder : items) {
            joiner.add(verifiedOrder.getStatus());
        }

        return joiner.toString();
    }

    public String getDiscountStatus() {
        StringJoiner joiner = new StringJoiner(ENTER);

        for (VerifiedOrder verifiedOrder : items) {
            String discountStatus = verifiedOrder.getDiscountStatus();
            if (discountStatus != null) {
                joiner.add(discountStatus);
            }
        }

        return joiner.toString();
    }

    public void apply() {
        items.forEach(VerifiedOrder::applyStock);
    }
}
