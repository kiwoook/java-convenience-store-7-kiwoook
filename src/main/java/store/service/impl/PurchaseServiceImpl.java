package store.service.impl;

import store.dto.CheckPurchaseInfosDto;
import store.dto.ReceiptDto;
import store.enums.Confirmation;
import store.service.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {


    @Override
    public void create(String input) {
        // 제품들을 분리하여 Map 으로 정리한다.
    }

    @Override
    public void validateQuantity() {

    }

    @Override
    public CheckPurchaseInfosDto check() {
        return null;
    }

    @Override
    public ReceiptDto getReceipt(CheckPurchaseInfosDto infoDto, Confirmation membershipConfirmation) {
        return null;
    }
}
