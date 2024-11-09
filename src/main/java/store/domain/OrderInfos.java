package store.domain;

import static store.enums.ErrorMessage.INVALID_PURCHASE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class OrderInfos {

    private static final String SEPARATOR = ",";
    private final List<OrderInfo> items;

    public OrderInfos() {
        this.items = new ArrayList<>();
    }

    public OrderInfos(List<OrderInfo> items) {
        this.items = items;
    }

    public static OrderInfos create(String input) {
        validInput(input);
        return new OrderInfos(mergeOrderInfos(input));
    }

    private static void validInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }

        boolean startsWith = input.startsWith(SEPARATOR);
        boolean endsWith = input.endsWith(SEPARATOR);

        if (startsWith || endsWith) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private static List<OrderInfo> mergeOrderInfos(String input) {
        List<OrderInfo> orderInfos = parsePurchaseInfos(input);
        return OrderInfo.sumQuantityByProduct(orderInfos)
                .entrySet()
                .stream()
                .map(entry -> OrderInfo.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    private static List<OrderInfo> parsePurchaseInfos(String input) {
        return Arrays.stream(input.split(SEPARATOR))
                .map(OrderInfo::create)
                .toList();
    }

    public void forEach(Consumer<OrderInfo> purchaseInfo) {
        items.forEach(purchaseInfo);
    }

    public Stream<OrderInfo> stream() {
        return this.items.stream();
    }

    protected void addPurchaseInfo(OrderInfo orderInfo) {
        items.add(orderInfo);
    }

    protected int size() {
        return items.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderInfos that = (OrderInfos) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(items);
    }
}
