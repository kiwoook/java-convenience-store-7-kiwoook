package store.controller;

import java.io.IOException;
import store.dto.Message;
import store.service.ClearService;
import store.service.StoreService;
import store.viewer.OutputViewer;

public class StoreController {

    private final StoreService storeService;
    private final ClearService clearService;
    private final OutputViewer outputViewer;

    public StoreController(StoreService storeService, ClearService clearService,
                           OutputViewer outputViewer) {
        this.storeService = storeService;
        this.clearService = clearService;
        this.outputViewer = outputViewer;
    }

    public void init() throws IOException {
        storeService.savePromotion();
        storeService.saveProduct();
    }

    public void explain() {
        outputViewer.printExplain();
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
