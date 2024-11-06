package store.utils;

import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import store.dto.ProductDto;
import store.dto.ProductDtos;
import store.dto.PromotionDto;
import store.dto.PromotionDtos;
import store.exception.InvalidFormatException;

public class FileHandler {
    public static final String SEPARATOR = ",";
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";
    private static final int PROMOTION_FILED_COUNT = 5;
    private static final int PRODUCT_FILED_COUNT = 4;

    public ProductDtos readProductFile() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PRODUCT_FILE_PATH))) {
            String readLine = "";
            List<ProductDto> productDtoList = new ArrayList<>();

            while ((readLine = bufferedReader.readLine()) != null) {
                String[] splitLine = split(readLine, PRODUCT_FILED_COUNT);
                productDtoList.add(toProductDto(splitLine));
            }
            return new ProductDtos(productDtoList);
        }
    }

    public PromotionDtos readPromotionFile() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PROMOTION_FILE_PATH))) {
            String readLine = "";
            List<PromotionDto> promotionList = new ArrayList<>();

            while ((readLine = bufferedReader.readLine()) != null) {
                String[] splitLine = split(readLine, PROMOTION_FILED_COUNT);
                promotionList.add(toPromotionDto(splitLine));
            }
            return new PromotionDtos(promotionList);
        }
    }

    private void validLine(String input) {
        if (input == null || input.isBlank()) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }

        boolean startsWith = input.startsWith(SEPARATOR);
        boolean endsWith = input.endsWith(SEPARATOR);

        if (startsWith || endsWith) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }
    }

    protected String[] split(String input, int length) {
        validLine(input);
        String[] splitInput = input.split(SEPARATOR);

        if (splitInput.length != length) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }

        return splitInput;
    }

    protected PromotionDto toPromotionDto(String[] splitLine) {
        try {
            String name = splitLine[0];
            long buy = Long.parseLong(splitLine[1]);
            long get = Long.parseLong(splitLine[2]);
            LocalDate startDate = LocalDate.parse(splitLine[3]);
            LocalDate endDate = LocalDate.parse(splitLine[4]);
            return new PromotionDto(name, buy, get, startDate, endDate);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }
    }

    protected ProductDto toProductDto(String[] splitLine) {
        try {
            String name = splitLine[0];
            long price = Long.parseLong(splitLine[1]);
            long quantity = Long.parseLong(splitLine[2]);
            String promotionName = splitLine[3];

            return new ProductDto(name, price, quantity, promotionName);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }

    }


}
