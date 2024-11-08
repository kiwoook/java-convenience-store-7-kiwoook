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

    private OrderInfos(String input) {
        validInput(input);
        this.items = mergePurchaseInfos(input);
    }

    public static OrderInfos create(String input) {
        return new OrderInfos(input);
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

    private void validInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }

        boolean startsWith = input.startsWith(SEPARATOR);
        boolean endsWith = input.endsWith(SEPARATOR);

        if (startsWith || endsWith) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private List<OrderInfo> mergePurchaseInfos(String input) {
        List<OrderInfo> orderInfos = parsePurchaseInfos(input);
        return OrderInfo.sumQuantityByProduct(orderInfos)
                .entrySet()
                .stream()
                .map(entry -> OrderInfo.of(entry.getKey(), entry.getValue()))
                .toList();
    }


    private List<OrderInfo> parsePurchaseInfos(String input) {
        return Arrays.stream(input.split(SEPARATOR))
                .map(OrderInfo::create)
                .toList();
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
