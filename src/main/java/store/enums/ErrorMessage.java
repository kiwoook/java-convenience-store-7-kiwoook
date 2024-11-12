package store.enums;

public enum ErrorMessage {
    INVALID_PURCHASE("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    EXCEED_PURCHASE("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NOT_EXIST_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    INVALID_FILE_FORMAT("비정상적인 형식입니다."),

    NOT_SAVE_PURCHASE_INFO("구매 내역이 저장되지 않았습니다");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
