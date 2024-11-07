package store.enums;

import static store.utils.Constants.ENTER;

public enum PromptMessage {
    EXPLAIN("안녕하세요. W편의점입니다." + ENTER + "현재 보유하고 있는 상품입니다." + ENTER),
    BUY("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    MEMBERSHIP_DISCOUNT("멤버십 할인을 받으시겠습니까? (Y/N)"),
    RETRY_PURCHASE("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

    private final String message;

    PromptMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
