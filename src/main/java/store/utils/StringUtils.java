package store.utils;

import static store.enums.ErrorMessage.INVALID_INPUT;

import java.text.DecimalFormat;

public class StringUtils {

    private StringUtils() {
    }

    public static String numberFormat(long price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        return decimalFormat.format(price);
    }

    public static void validName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }

}
