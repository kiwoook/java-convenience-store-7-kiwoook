package store.dto;

public record OrderConfirmDto(String name, long requestQuantity, long problemQuantity) {

    public static OrderConfirmDto create(String name, long requestQuantity, long problemQuantity) {
        return new OrderConfirmDto(name, requestQuantity, problemQuantity);
    }
}
