package store.model;

import static store.utils.Constants.ENTER;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

public class Products {

    private final Map<String, Product> items;

    public Products() {
        this.items = new LinkedHashMap<>();
    }

    public static Products create() {
        return new Products();
    }

    public Optional<Product> get(String productName) {
        return Optional.ofNullable(items.get(productName));
    }

    public void put(String productName, Long price, Long quantity, Promotion promotion) {
        Product product = items.get(productName);
        if (product == null) {
            product = new Product(productName, price, promotion);
        }

        product.addStock(quantity, promotion);

        items.put(productName, product);
    }

    public String statusAll() {
        StringJoiner joiner = new StringJoiner(ENTER);

        for (Product product : items.values()) {
            joiner.add(product.getStatus());
        }

        return joiner.toString();
    }

    @Override
    public String toString() {
        return "Products{" +
                "items=" + items +
                '}';
    }
}
