package store.controller;

import java.io.IOException;
import store.dto.Message;
import store.enums.PromptMessage;
import store.service.StoreService;
import store.service.impl.ClearService;
import store.viewer.InputViewer;
import store.viewer.OutputViewer;

public class StoreController {

    private final StoreService storeService;
    private final ClearService clearService;
    private final InputViewer inputViewer;
    private final OutputViewer outputViewer;

    public StoreController(StoreService storeService, ClearService clearService, InputViewer inputViewer,
                           OutputViewer outputViewer) {
        this.storeService = storeService;
        this.clearService = clearService;
        this.inputViewer = inputViewer;
        this.outputViewer = outputViewer;
    }

    public void init() throws IOException {
        storeService.savePromotion();
        storeService.saveProduct();
    }

    public void explain() {
        inputViewer.promptMessage(PromptMessage.EXPLAIN);
        Message inventoryStatus = storeService.getInventoryStatus();
        outputViewer.printResult(inventoryStatus);
    }

    public void clearFile() {
        clearService.clearFile();
    }

    public void clearOrder() {
        clearService.clearOrder();
    }

}
