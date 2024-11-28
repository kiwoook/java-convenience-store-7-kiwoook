package store.dto;

public record ProductDto(String name, Long price, Long quantity, String promotionName) {

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotionName='" + promotionName + '\'' +
                '}';
    }
}
