package store.service.impl;

import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;

import java.io.IOException;
import java.time.LocalDate;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.dto.InventoryStatusDto;
import store.dto.ProductDto;
import store.dto.ProductDtos;
import store.dto.PromotionDto;
import store.dto.PromotionDtos;
import store.exception.InvalidFormatException;
import store.repository.MapRepository;
import store.service.StoreService;
import store.utils.FileHandler;

public class StoreServiceImpl implements StoreService {

    private final MapRepository<Product> productRepository;
    private final MapRepository<Promotion> promotionRepository;
    private final FileHandler fileHandler;

    public StoreServiceImpl(MapRepository<Product> productRepository, MapRepository<Promotion> promotionRepository,
                            FileHandler fileHandler) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.fileHandler = fileHandler;
    }

    @Override
    public void savePromotion() throws IOException {
        PromotionDtos promotionDtos = fileHandler.readPromotionFile();

        for (PromotionDto promotionDto : promotionDtos.getPromotionDtoList()) {
            Promotion promotion = promotionDto.toPromotion();
            promotionRepository.save(promotionDto.name(), promotion);
        }
    }

    @Override
    public void saveProduct() throws IOException {
        ProductDtos productDtos = fileHandler.readProductFile();

        for (ProductDto productDto : productDtos.getProductDtoList()) {
            Product product = createProduct(productDto);

            if (product == null) {
                continue;
            }
            productRepository.save(productDto.name(), product);
        }
    }

    @Override
    public InventoryStatusDto getInventoryStatus() {
        Products products = Products.create(productRepository.getAll());

        return new InventoryStatusDto(products.toString());
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        if (productDto.promotionName() == null) {
            return createNormalProduct(productDto);
        }
        return createPromotionProduct(productDto);

    }

    private Product createNormalProduct(ProductDto productDto) {
        Product product = getProduct(productDto, null);
        product.validPrice(productDto.price());
        product.addNormalQuantity(productDto.quantity());

        return product;
    }

    private Product createPromotionProduct(ProductDto productDto) {
        Promotion promotion = getPromotion(productDto.promotionName());

        if (!promotion.isValidPromotion(LocalDate.now())) {
            return null;
        }

        Product product = getProduct(productDto, promotion);
        product.validPrice(productDto.price());
        product.updatePromotion(promotion);
        product.addPromotionQuantity(productDto.quantity());
        return product;
    }

    private Promotion getPromotion(String promotionName) {
        return promotionRepository.findById(promotionName).orElseThrow(() ->
                new InvalidFormatException(INVALID_FILE_FORMAT.getMessage()));
    }

    private Product getProduct(ProductDto productDto, Promotion promotion) {
        return productRepository.findById(productDto.name())
                .orElse(new Product(productDto.name(), productDto.price(), promotion));
    }
}