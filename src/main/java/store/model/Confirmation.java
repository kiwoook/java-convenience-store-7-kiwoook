package store.model;

import java.util.Arrays;
import store.utils.ErrorMessage;

public enum Confirmation {

    YES("Y"),
    NO("N");

    private final String value;

    Confirmation(String value) {
        this.value = value;
    }

    public static Confirmation of(String input) {
        return Arrays.stream(values())
                .filter(confirmation -> confirmation.value.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage()));
    }

}
