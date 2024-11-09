package store.service.impl;

import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.IOException;
import java.time.LocalDate;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.dto.Message;
import store.dto.ProductDto;
import store.dto.ProductDtos;
import store.dto.PromotionDto;
import store.dto.PromotionDtos;
import store.exception.InvalidFileFormatException;
import store.repository.PromotionRepository;
import store.repository.ProductRepository;
import store.service.StoreService;
import store.utils.FileHandler;

public class StoreServiceImpl implements StoreService {

    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final FileHandler fileHandler;

    public StoreServiceImpl(ProductRepository productRepository, PromotionRepository promotionRepository,
                            FileHandler fileHandler) {

        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.fileHandler = fileHandler;
    }

    @Override
    public void savePromotion() throws IOException {
        PromotionDtos promotionDtos = fileHandler.readPromotionFile();

        for (PromotionDto promotionDto : promotionDtos.items()) {
            Promotion promotion = promotionDto.toPromotion();
            promotionRepository.save(promotionDto.name(), promotion);
        }
    }

    @Override
    public void saveProduct() throws IOException {
        ProductDtos productDtos = fileHandler.readProductFile();

        for (ProductDto productDto : productDtos.items()) {
            Product product = createProduct(productDto);
            if (product == null) {
                continue;
            }
            productRepository.save(productDto.name(), product);
        }
    }

    @Override
    public Message getInventoryStatus() {
        Products products = productRepository.getAll();

        return new Message(products.toString());
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        if (productDto.promotionName() == null) {
            return createNormalProduct(productDto);
        }

        LocalDate currentDate = getCurrentDate();
        return createPromotionProduct(productDto, currentDate);
    }

    private Product createNormalProduct(ProductDto productDto) {
        Product product = getProduct(productDto, null);
        product.validPrice(productDto.price());
        product.addNormalQuantity(productDto.quantity());

        return product;
    }

    private Product createPromotionProduct(ProductDto productDto, LocalDate currentDate) {
        Promotion promotion = getPromotion(productDto.promotionName());

        if (!promotion.isValidPromotion(currentDate)) {
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
                new InvalidFileFormatException(INVALID_FILE_FORMAT.getMessage()));
    }

    private Product getProduct(ProductDto productDto, Promotion promotion) {
        return productRepository.findByProductName(productDto.name())
                .orElse(new Product(productDto.name(), productDto.price(), promotion));
    }

    private LocalDate getCurrentDate() {
        return LocalDate.from(DateTimes.now());
    }
}
