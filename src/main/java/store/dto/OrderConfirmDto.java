package store.dto;

import store.domain.vo.ProductName;

public record OrderConfirmDto(ProductName name, long requestQuantity, long problemQuantity) {

    public static OrderConfirmDto create(ProductName name, long requestQuantity, long problemQuantity) {
        return new OrderConfirmDto(name, requestQuantity, problemQuantity);
    }
}
