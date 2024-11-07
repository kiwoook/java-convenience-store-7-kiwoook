package store.controller;

import static store.enums.PromptMessage.BUY;
import static store.enums.PromptMessage.MEMBERSHIP_DISCOUNT;
import static store.enums.PromptMessage.RETRY_PURCHASE;

import store.dto.PurchaseConfirmDto;
import store.dto.PurchaseConfirmDtos;
import store.dto.ReceiptDto;
import store.enums.Confirmation;
import store.service.PurchaseService;
import store.utils.RecoveryUtils;
import store.viewer.InputViewer;
import store.viewer.OutputViewer;

public class PurchaseController {

    private final PurchaseService purchaseService;
    private final InputViewer inputViewer;
    private final OutputViewer outputViewer;

    public PurchaseController(PurchaseService purchaseService, InputViewer inputViewer, OutputViewer outputViewer) {
        this.purchaseService = purchaseService;
        this.inputViewer = inputViewer;
        this.outputViewer = outputViewer;
    }

    public void buy() {
        inputViewer.promptMessage(BUY);
        RecoveryUtils.executeWithRetry(inputViewer::getInput, purchaseService::create);
    }

    public void check() {
        PurchaseConfirmDtos confirmDtos = purchaseService.check();
        for (PurchaseConfirmDto confirmDto : confirmDtos.getCheckPurchaseInfoDtos()) {
            if (confirmDto.problemQuantity() == 0) {
                purchaseService.processQuantity(confirmDto);
                continue;
            }
            confirmProblemQuantity(confirmDto);
        }
    }

    public void printReceipt() {
        inputViewer.promptMessage(MEMBERSHIP_DISCOUNT);

        ReceiptDto receiptDto = RecoveryUtils.executeWithRetry(() -> Confirmation.from(inputViewer.getInput()),
                purchaseService::getReceipt);
    }

    public boolean retry() {
        inputViewer.promptMessage(RETRY_PURCHASE);
        Confirmation confirmation = RecoveryUtils.executeWithRetry(() -> Confirmation.from(inputViewer.getInput()));
        return confirmation.isBool();
    }

    private void printProblemQuantity(PurchaseConfirmDto confirmDto) {
        if (confirmDto.problemQuantity() < 0) {
            inputViewer.promptNonDiscount(confirmDto.name(), -confirmDto.problemQuantity());
        }

        if (confirmDto.problemQuantity() > 0) {
            inputViewer.promptFreeItem(confirmDto.name(), confirmDto.problemQuantity());
        }
    }

    private void confirmProblemQuantity(PurchaseConfirmDto confirmDto) {
        printProblemQuantity(confirmDto);
        Confirmation confirmation = RecoveryUtils.executeWithRetry(() -> Confirmation.from(inputViewer.getInput()));
        purchaseService.processProblemQuantity(confirmDto, confirmation);
    }
}
