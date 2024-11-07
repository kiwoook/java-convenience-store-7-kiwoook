package store.service;

import store.dto.CheckPurchaseInfosDto;
import store.dto.ReceiptDto;
import store.enums.Confirmation;

public interface PurchaseService {

    // input을 받고 유효한 구매 LIST로 변환한다.
    void create(String input);

    // 수량들을 체크하여 컨트롤러에 전송한다.
    CheckPurchaseInfosDto check();

    // 다시 DTO를 받고 영수증을 반환한다. (멤버십 할인 유무 체크 후)
    ReceiptDto getReceipt(CheckPurchaseInfosDto infoDto, Confirmation confirmation);
}
