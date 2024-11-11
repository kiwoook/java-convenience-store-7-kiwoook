package store.dto;

import store.domain.vo.ProductName;

public record ProductDto(ProductName name, long price, long quantity, String promotionName) {
}
