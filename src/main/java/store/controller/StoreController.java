package store.controller;

import java.io.IOException;
import store.dto.InventoryStatusDto;
import store.enums.PromptMessage;
import store.service.StoreService;
import store.viewer.InputViewer;
import store.viewer.OutputViewer;

public class StoreController {

    private final StoreService storeService;
    private final InputViewer inputViewer;
    private final OutputViewer outputViewer;

    public StoreController(StoreService storeService, InputViewer inputViewer, OutputViewer outputViewer) {
        this.storeService = storeService;
        this.inputViewer = inputViewer;
        this.outputViewer = outputViewer;
    }

    public void init() throws IOException {
        storeService.savePromotion();
        storeService.saveProduct();
    }

    public void explain() {
        inputViewer.promptMessage(PromptMessage.EXPLAIN);
        InventoryStatusDto inventoryStatus = storeService.getInventoryStatus();
        outputViewer.printResult(inventoryStatus.message());
    }

}
