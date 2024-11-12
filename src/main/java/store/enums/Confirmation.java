package store.enums;

import static store.enums.ErrorMessage.INVALID_INPUT;

public enum Confirmation {
    YES("Y", true),
    NO("N", false);

    private final String input;
    private final boolean bool;

    Confirmation(String input, boolean bool) {
        this.input = input;
        this.bool = bool;
    }

    public static Confirmation from(String input) {
        if (input.equals(YES.input)) {
            return YES;
        }

        if (input.equals(NO.input)) {
            return NO;
        }

        throw new IllegalArgumentException(INVALID_INPUT.getMessage());
    }

    public boolean isBool() {
        return bool;
    }
}
