package store.utils;

import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;
import static store.utils.Constants.PRODUCT_FILE_PATH;
import static store.utils.Constants.PROMOTION_FILE_PATH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import store.domain.vo.ProductName;
import store.dto.ProductDto;
import store.dto.ProductDtos;
import store.dto.PromotionDto;
import store.dto.PromotionDtos;
import store.exception.InvalidFormatException;

public class FileHandler {
    public static final String SEPARATOR = ",";

    private static final int PROMOTION_FILED_COUNT = 5;
    private static final int PRODUCT_FILED_COUNT = 4;

    public <T> List<T> readFile(String filePath, int fieldCount, Function<String[], T> function) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            List<T> dtoList = new ArrayList<>();
            skipFirstLine(bufferedReader);
            String readLine;

            while ((readLine = bufferedReader.readLine()) != null) {
                String[] splitLine = splitLine(readLine, fieldCount);
                dtoList.add(function.apply(splitLine));
            }
            return dtoList;
        }
    }

    public void skipFirstLine(BufferedReader bufferedReader) throws IOException {
        bufferedReader.readLine();
    }

    public ProductDtos readProductFile() throws IOException {
        return new ProductDtos(readFile(PRODUCT_FILE_PATH, PRODUCT_FILED_COUNT, this::toProductDto));
    }

    public PromotionDtos readPromotionFile() throws IOException {
        return new PromotionDtos(readFile(PROMOTION_FILE_PATH, PROMOTION_FILED_COUNT, this::toPromotionDto));
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

    protected String[] splitLine(String input, int length) {
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
            String name = checkProductName(splitLine[0]);
            long price = Long.parseLong(splitLine[1]);
            long quantity = Long.parseLong(splitLine[2]);
            String promotionName = parsePromotionName(splitLine[3]);

            return new ProductDto(ProductName.create(name), price, quantity, promotionName);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }

    }

    private String checkProductName(String name) {
        if (name.isBlank()) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }

        return name;
    }

    private String parsePromotionName(String promotionName) {
        if (promotionName.equals("null")) {
            return null;
        }
        return promotionName;
    }

}
