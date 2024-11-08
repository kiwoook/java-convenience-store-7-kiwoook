package store.domain;

import static store.enums.ErrorMessage.INVALID_PURCHASE;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import store.dto.OrderConfirmDto;

public class OrderInfo {

    private static final int MIN_LENGTH = 5;
    private static final String OPEN_BRACKET = "[";
    private static final String CLOSE_BRACKET = "]";
    private static final String SEPARATOR = "-";
    private static final int BRACKET_OFFSET = 1;
    private static final int NAME_IDX = 0;
    private static final int QUANTITY_IDX = 1;
    private static final int SPLIT_LENGTH = 2;
    private static final int ZERO = 0;

    private String productName;
    private long requestQuantity;

    public OrderInfo(String input) {
        validInput(input);
        parseNameAndQuantity(input);
    }

    public OrderInfo(String productName, long requestQuantity) {
        this.productName = productName;
        this.requestQuantity = requestQuantity;
    }

    public static OrderInfo create(String input) {
        return new OrderInfo(input);
    }

    protected static Map<String, Long> totalPurchaseCount(List<OrderInfo> orderInfos) {
        Map<String, Long> purchaseCountMap = new LinkedHashMap<>();

        orderInfos.forEach(
                orderInfo -> purchaseCountMap.merge(orderInfo.productName, orderInfo.requestQuantity,
                        Long::sum));

        return purchaseCountMap;
    }

    public OrderConfirmDto toConfirmDto(Product product) {
        long requireQuantity = product.calculateRequiredQuantity(this.requestQuantity);

        return OrderConfirmDto.create(this.productName, this.requestQuantity, requireQuantity);
    }

    public void validQuantity(Product product) {
        product.validStock(this.requestQuantity);
    }

    public void parseNameAndQuantity(String input) {
        String trimInput = trimBrackets(input);
        validTrimInput(trimInput);
        String[] splitInput = trimInput.split(SEPARATOR);
        validSplitInput(splitInput);

        this.productName = splitInput[NAME_IDX];
        this.requestQuantity = parseQuantity(splitInput[QUANTITY_IDX]);
    }

    private void validTrimInput(String trimInput) {
        boolean startsWith = trimInput.startsWith(SEPARATOR);
        boolean endsWith = trimInput.endsWith(SEPARATOR);

        if (startsWith || endsWith) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private String trimBrackets(String input) {
        int endIdx = input.length() - BRACKET_OFFSET;
        return input.substring(BRACKET_OFFSET, endIdx);
    }

    private long parseQuantity(String number) {
        try {
            long parseNumber = Long.parseLong(number);
            validQuantity(parseNumber);
            return parseNumber;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private void validQuantity(long number) {
        if (number <= ZERO) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private void validSplitInput(String[] splitInput) {
        if (splitInput.length != SPLIT_LENGTH) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }

        if (splitInput[NAME_IDX].isBlank() || splitInput[QUANTITY_IDX].isBlank()) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private void validInput(String input) {
        if (input == null || input.isBlank() || input.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }

        boolean startsWith = input.startsWith(OPEN_BRACKET);
        boolean endsWith = input.endsWith(CLOSE_BRACKET);
        if ((!startsWith || !endsWith) && input.contains(SEPARATOR)) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    public String toOriginalInput() {
        return OPEN_BRACKET + productName + SEPARATOR + requestQuantity + CLOSE_BRACKET;
    }

    @Override
    public String toString() {
        return productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderInfo that = (OrderInfo) o;
        return Objects.equals(productName, that.productName) && Objects.equals(requestQuantity,
                that.requestQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, requestQuantity);
    }
}
