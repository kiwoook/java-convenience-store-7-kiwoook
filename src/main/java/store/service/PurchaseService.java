package store.service;

import store.dto.PurchaseConfirmDto;
import store.dto.PurchaseConfirmDtos;
import store.dto.ReceiptDto;
import store.enums.Confirmation;

public interface PurchaseService {

    void create(String input);

    PurchaseConfirmDtos check();

    void processQuantity(PurchaseConfirmDto purchaseConfirmDto);

    void processProblemQuantity(PurchaseConfirmDto purchaseConfirmDto, Confirmation confirmation);

    ReceiptDto getReceipt(Confirmation confirmation);
}
