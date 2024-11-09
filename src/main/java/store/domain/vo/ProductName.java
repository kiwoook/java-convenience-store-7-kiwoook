package store.domain.vo;

import static store.enums.ErrorMessage.INVALID_INPUT;

public record ProductName(String value) {

    public ProductName {
        validName(value);
    }

    public static ProductName create(String value) {
        return new ProductName(value);
    }

    public void validName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
