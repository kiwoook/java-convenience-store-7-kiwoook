package store.utils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String OR = "|";

    private StringUtils() {
    }

    public static String[] split(String regex, String input, int fieldCount) {
        if (regex == null || input == null || input.isBlank() || input.startsWith(regex)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }

        String[] split = input.split(regex);
        if (split.length != fieldCount) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
        return split;
    }

    public static String regexSeparators(List<String> separators) {
        StringJoiner regex = new StringJoiner(OR);

        for (String separator : separators) {
            regex.add(Pattern.quote(separator));
        }

        return regex.toString();
    }

    public static String regexSeparators(String separator, String... separators) {
        StringJoiner regex = new StringJoiner(OR);
        regex.add(separator);

        for (String sep : separators) {
            regex.add(Pattern.quote(sep));
        }

        return regex.toString();
    }

    public static String numberFormat(long number) {
        DecimalFormat format = new DecimalFormat("#,###");

        return format.format(number);
    }

}
