package store.service;

import java.io.IOException;
import store.domain.Product;
import store.dto.Message;
import store.dto.ProductDto;

public interface StoreService {

    void savePromotion() throws IOException;

    void saveProduct() throws IOException;

    Message getInventoryStatus();

    Product createProduct(ProductDto productDto);
}
