package store.dto;

public record Message(String message) {

    @Override
    public String toString() {
        return message;
    }
}
