package store.model;

import store.utils.ErrorMessage;

public record PurchaseInfo(String productName, Long requestQuantity) {

    public static PurchaseInfo from(String input) {
        try {
            String[] split = parseInput(input);

            return new PurchaseInfo(split[0], Long.parseLong(split[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_REQUEST_PRODUCT.getMessage());
        }
    }


    public static void validInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_REQUEST_PRODUCT.getMessage());
        }

        boolean startsWith = input.startsWith("[");
        boolean endsWith = input.endsWith("]");

        if (!startsWith || !endsWith) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_REQUEST_PRODUCT.getMessage());
        }

        if (input.length() < 5 || input.split("-").length != 2) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_REQUEST_PRODUCT.getMessage());
        }
    }

    public static String[] parseInput(String input) {
        validInput(input);
        String substring = input.substring(1, input.length() - 1);
        return substring.split("-");
    }

    @Override
    public String toString() {
        return "PurchaseInfo{" +
                "productName='" + productName + '\'' +
                ", requestQuantity=" + requestQuantity +
                '}';
    }
}
