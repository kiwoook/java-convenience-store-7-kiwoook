package store.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import store.dto.ProductDto;
import store.dto.PromotionDto;

public class FileHandler {

    private static final String SEPARATOR = ",";

    private static final String PRODUCT_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_PATH = "src/main/resources/promotions.md";

    public List<ProductDto> readProductFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PRODUCT_PATH))) {
            return bufferedReader.lines()
                    .skip(1)
                    .map(this::toProductDto)
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<PromotionDto> readPromotionDto() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PROMOTIONS_PATH))) {
            return bufferedReader.lines()
                    .skip(1)
                    .map(this::toPromotionDto)
                    .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private PromotionDto toPromotionDto(String line) {
        String[] split = StringUtils.split(SEPARATOR, line, 5);

        return parsePromotionDto(split);
    }

    private PromotionDto parsePromotionDto(String[] split) {
        try {
            String name = split[0];
            int buy = Integer.parseInt(split[1]);
            validGetPromotion(Long.parseLong(split[2]));
            LocalDate startDate = LocalDate.parse(split[3]);
            LocalDate endDate = LocalDate.parse(split[4]);

            return new PromotionDto(name, buy, startDate, endDate);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT.getMessage());
        }
    }

    private void validGetPromotion(Long get) {
        if (get != 1) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT.getMessage());
        }
    }

    private ProductDto toProductDto(String line) {
        String[] split = StringUtils.split(SEPARATOR, line, 4);

        return parseProductDto(split);
    }

    private ProductDto parseProductDto(String[] split) {
        try {
            String name = split[0];
            long price = Long.parseLong(split[1]);
            long quantity = Long.parseLong(split[2]);
            String promotionName = split[3];
            return new ProductDto(name, price, quantity, promotionName);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FILE_FORMAT.getMessage());
        }
    }


}
