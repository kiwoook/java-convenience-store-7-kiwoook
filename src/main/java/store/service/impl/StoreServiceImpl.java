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
import store.exception.InvalidFormatException;
import store.repository.MapRepository;
import store.service.StoreService;
import store.utils.FileHandler;

public class StoreServiceImpl implements StoreService {

    private final MapRepository<Product> productMapRepository;
    private final MapRepository<Promotion> promotionMapRepository;
    private final FileHandler fileHandler;

    public StoreServiceImpl(MapRepository<Product> productMapRepository, MapRepository<Promotion> promotionMapRepository,
                            FileHandler fileHandler) {
        this.productMapRepository = productMapRepository;
        this.promotionMapRepository = promotionMapRepository;
        this.fileHandler = fileHandler;
    }

    @Override
    public void savePromotion() throws IOException {
        PromotionDtos promotionDtos = fileHandler.readPromotionFile();

        for (PromotionDto promotionDto : promotionDtos.items()) {
            Promotion promotion = promotionDto.toPromotion();
            promotionMapRepository.save(promotionDto.name(), promotion);
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
            productMapRepository.save(productDto.name(), product);
        }
    }

    @Override
    public Message getInventoryStatus() {
        Products products = Products.create(productMapRepository.getAll());

        return new Message(products.toString());
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        if (productDto.promotionName() == null) {
            return createNormalProduct(productDto);
        }

        LocalDate currentDate = getCurrentDate();
        return createPromotionProduct(productDto,currentDate);

    }

    private Product createNormalProduct(ProductDto productDto) {
        Product product = getProduct(productDto, null);
        product.validPrice(productDto.price());
        product.addNormalQuantity(productDto.quantity());

        return product;
    }

    public Product createPromotionProduct(ProductDto productDto, LocalDate currentDate) {
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
        return promotionMapRepository.findById(promotionName).orElseThrow(() ->
                new InvalidFormatException(INVALID_FILE_FORMAT.getMessage()));
    }

    private Product getProduct(ProductDto productDto, Promotion promotion) {
        return productMapRepository.findById(productDto.name())
                .orElse(new Product(productDto.name(), productDto.price(), promotion));
    }

    private LocalDate getCurrentDate(){
        return LocalDate.from(DateTimes.now());
    }
}
