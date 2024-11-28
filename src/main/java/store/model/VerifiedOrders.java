package store.model;

import static store.utils.Constants.ENTER;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class VerifiedOrders {
    private final List<VerifiedOrder> items;

    public VerifiedOrders() {
        this.items = new ArrayList<>();
    }

    public static VerifiedOrders create() {
        return new VerifiedOrders();
    }

    public void addProductByGiftQuantity(Product product, Long requestQuantity, Long problemQuantity,
                                         Confirmation confirmation) {
        if (confirmation.equals(Confirmation.YES)) {
            requestQuantity += problemQuantity;
        }

        items.add(VerifiedOrder.of(product, requestQuantity));
    }

    public void addProductByOriginalPriceQuantity(Product product, Long requestQuantity, Long problemQuantity,
                                                  Confirmation confirmation) {
        if (confirmation.equals(Confirmation.NO)) {
            requestQuantity += problemQuantity;
        }

        items.add(VerifiedOrder.of(product, requestQuantity));
    }

    public void addProduct(Product product, Long requestQuantity) {
        items.add(VerifiedOrder.of(product, requestQuantity));
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
                .mapToLong(VerifiedOrder::getTotalDiscountByProduct)
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
