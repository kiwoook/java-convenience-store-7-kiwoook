package store.domain;

import static store.enums.ErrorMessage.INVALID_PURCHASE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class OrderInfos {

    private static final String SEPARATOR = ",";
    private final List<OrderInfo> orderInfoList;

    public OrderInfos() {
        this.orderInfoList = new ArrayList<>();
    }

    public OrderInfos(String input) {
        validInput(input);
        this.orderInfoList = mergePurchaseInfos(input);
    }

    public static OrderInfos create(String input) {
        return new OrderInfos(input);
    }

    private List<OrderInfo> mergePurchaseInfos(String input) {
        List<OrderInfo> orderInfos = parsePurchaseInfos(input);
        return OrderInfo.totalPurchaseCount(orderInfos)
                .entrySet()
                .stream()
                .map(entry -> new OrderInfo(entry.getKey(), entry.getValue()))
                .toList();
    }

    private List<OrderInfo> parsePurchaseInfos(String input) {
        return Arrays.stream(input.split(SEPARATOR))
                .map(OrderInfo::create)
                .toList();
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

    public void addPurchaseInfo(OrderInfo orderInfo) {
        orderInfoList.add(orderInfo);
    }

    public void forEach(Consumer<OrderInfo> purchaseInfo) {
        orderInfoList.forEach(purchaseInfo);
    }

    public int size() {
        return orderInfoList.size();
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
        return Objects.equals(orderInfoList, that.orderInfoList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderInfoList);
    }
}
