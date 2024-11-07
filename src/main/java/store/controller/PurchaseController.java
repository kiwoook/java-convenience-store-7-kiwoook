package store.controller;

import static store.enums.PromptMessage.BUY;

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
}
