package store.utils;

import java.text.DecimalFormat;

public class StringUtils {

    private StringUtils() {
    }

    public static String numberFormat(long price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        return decimalFormat.format(price);
    }

}
