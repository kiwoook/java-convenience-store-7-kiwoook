package store.dto;

import java.util.List;

public class ProductDtos {
    List<ProductDto> productList;

    public ProductDtos(List<ProductDto> productList) {
        this.productList = productList;
    }

    public List<ProductDto> getProductList() {
        return productList;
    }
}
