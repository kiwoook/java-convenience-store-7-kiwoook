package store.dto;

public record PurchaseConfirmDto(String name, Long requestQuantity, Long problemQuantity) {

    public static PurchaseConfirmDto create(String name, Long requestQuantity, Long problemQuantity) {
        return new PurchaseConfirmDto(name, requestQuantity, problemQuantity);
    }
}
