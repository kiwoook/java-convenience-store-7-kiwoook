package store.dto;

import java.util.List;

public class ProductDtos {
    List<ProductDto> productDtoList;

    public ProductDtos(List<ProductDto> productList) {
        this.productDtoList = productList;
    }

    public List<ProductDto> getProductDtoList() {
        return productDtoList;
    }
}
