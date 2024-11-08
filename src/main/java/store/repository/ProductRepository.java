package store.repository;

import java.util.List;
import java.util.Optional;
import store.domain.Product;
import store.domain.Products;
import store.domain.vo.ProductName;

public interface ProductRepository {

    Optional<Product> findByProductName(ProductName productName);

    Product save(ProductName name, Product product);

    Products getAll();

    void clear();
}
