package store.service;

import java.io.IOException;
import store.domain.Product;
import store.dto.Message;
import store.dto.ProductDto;

public interface StoreService {

    // 파일을 읽어와 프로모션을 저장
    void savePromotion() throws IOException;

    // 파일을 읽어와 제품을 저장
    void saveProduct() throws IOException;

    // 제품의 보유 상품을 출력하는 메소드
    Message getInventoryStatus();

    Product createProduct(ProductDto productDto);
}
