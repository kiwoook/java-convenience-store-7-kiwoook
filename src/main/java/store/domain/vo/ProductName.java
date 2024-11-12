package store.domain.vo;

import store.utils.StringUtils;

public record ProductName(String value) {

    public ProductName {
        StringUtils.validName(value);
    }

    public static ProductName create(String value) {
        return new ProductName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
