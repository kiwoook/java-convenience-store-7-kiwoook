package store.dto;

public record OrderItemDto(String productName, long price, long quantity, long giftQuantity) {
}
