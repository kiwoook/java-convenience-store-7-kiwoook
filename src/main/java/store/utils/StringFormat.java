package store.utils;

import java.text.DecimalFormat;

public class StringFormat {

    private StringFormat() {
    }

    public static String number(long price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        return decimalFormat.format(price);
    }
}
