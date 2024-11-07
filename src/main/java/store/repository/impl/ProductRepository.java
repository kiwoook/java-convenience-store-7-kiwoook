package store.repository.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import store.domain.Product;
import store.repository.MapRepository;

public class ProductRepository implements MapRepository<Product> {

    private final Map<String, Product> database = new LinkedHashMap<>();

    @Override
    public Optional<Product> findById(String key) {
        Product product = database.get(key);
        return Optional.ofNullable(product);
    }

    @Override
    public Product save(String key, Product object) {
        return database.put(key, object);
    }

    @Override
    public List<Product> getAll() {
        return database.values()
                .stream()
                .toList();
    }
}
