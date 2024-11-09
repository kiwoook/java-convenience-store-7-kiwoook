package store.domain;

import static store.enums.ErrorMessage.INVALID_PURCHASE;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import store.domain.vo.ProductName;
import store.dto.OrderConfirmDto;

public class OrderInfo {

    private static final String OPEN_BRACKET = "[";
    private static final String CLOSE_BRACKET = "]";
    private static final String SEPARATOR = "-";

    private static final int MIN_LENGTH = 5;
    private static final int BRACKET_OFFSET = 1;
    private static final int NAME_IDX = 0;
    private static final int QUANTITY_IDX = 1;
    private static final int SPLIT_LENGTH = 2;
    private static final int ZERO = 0;

    private final ProductName productName;
    private final long requestQuantity;

    public OrderInfo(ProductName productName, long requestQuantity) {
        this.productName = productName;
        this.requestQuantity = requestQuantity;
    }

    public static OrderInfo of(ProductName productName, long requestQuantity) {
        return new OrderInfo(productName, requestQuantity);
    }

    public static OrderInfo create(String input) {
        validInput(input);
        String[] parsedInput = parseNameAndQuantity(input);
        return new OrderInfo(ProductName.create(parsedInput[NAME_IDX]), parseQuantity(parsedInput[QUANTITY_IDX]));
    }

    protected static Map<ProductName, Long> sumQuantityByProduct(List<OrderInfo> orderInfos) {
        Map<ProductName, Long> purchaseCountMap = new LinkedHashMap<>();

        orderInfos.forEach(
                orderInfo -> purchaseCountMap.merge(orderInfo.productName, orderInfo.requestQuantity,
                        Long::sum));

        return purchaseCountMap;
    }

    private static void validInput(String input) {
        if (input == null || input.isBlank() || input.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }

        boolean startsWith = input.startsWith(OPEN_BRACKET);
        boolean endsWith = input.endsWith(CLOSE_BRACKET);
        if ((!startsWith || !endsWith) && input.contains(SEPARATOR)) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private static String[] parseNameAndQuantity(String input) {
        String trimInput = trimBrackets(input);
        validTrimInput(trimInput);
        String[] splitInput = trimInput.split(SEPARATOR);
        validSplitInput(splitInput);

        return splitInput;
    }

    private static void validTrimInput(String trimInput) {
        boolean startsWith = trimInput.startsWith(SEPARATOR);
        boolean endsWith = trimInput.endsWith(SEPARATOR);

        if (startsWith || endsWith) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private static String trimBrackets(String input) {
        int endIdx = input.length() - BRACKET_OFFSET;

        return input.substring(BRACKET_OFFSET, endIdx);
    }

    private static long parseQuantity(String number) {
        try {
            long parseNumber = Long.parseLong(number);
            validQuantity(parseNumber);
            return parseNumber;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private static void validQuantity(long number) {
        if (number <= ZERO) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    private static void validSplitInput(String[] splitInput) {
        if (splitInput.length != SPLIT_LENGTH) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }

        if (splitInput[NAME_IDX].isBlank() || splitInput[QUANTITY_IDX].isBlank()) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    public OrderConfirmDto toConfirmDto(Product product) {
        long requireQuantity = product.calculateRequiredQuantity(requestQuantity);

        return OrderConfirmDto.create(productName, requestQuantity, requireQuantity);
    }

    public void validQuantity(Product product) {
        product.validStock(requestQuantity);
    }

    public String toOriginalInput() {
        return OPEN_BRACKET + productName + SEPARATOR + requestQuantity + CLOSE_BRACKET;
    }

    public ProductName getProductName() {
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
