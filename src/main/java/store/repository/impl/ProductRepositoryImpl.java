package store.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import store.domain.Product;
import store.domain.Products;
import store.domain.vo.ProductName;
import store.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

    private final Map<ProductName, Product> database = new HashMap<>();

    @Override
    public Optional<Product> findByProductName(ProductName productName) {
        return Optional.ofNullable(database.get(productName));
    }

    @Override
    public Product save(ProductName name, Product product) {
        database.put(name, product);
        return database.get(name);
    }

    @Override
    public Products getAll() {
        return new Products(database.values()
                .stream()
                .toList()
        );
    }

    @Override
    public void clear() {
        database.clear();
    }
}
