package store.enums;

public enum Confirmation {
    YES("Y"),
    NO("N");

    private final String input;

    Confirmation(String input) {
        this.input = input;
    }
}
