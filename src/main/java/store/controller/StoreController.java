package store.controller;

import java.io.IOException;
import store.dto.InventoryStatusDto;
import store.enums.PromptMessage;
import store.service.StoreService;
import store.viewer.Viewer;

public class StoreController {

    private final StoreService storeService;
    private final Viewer viewer;

    public StoreController(StoreService storeService, Viewer viewer) {
        this.storeService = storeService;
        this.viewer = viewer;
    }

    public void init() throws IOException {
        storeService.savePromotion();
        storeService.saveProduct();
    }

    public void explain() {
        viewer.promptMessage(PromptMessage.EXPLAIN);
        InventoryStatusDto inventoryStatus = storeService.getInventoryStatus();
        viewer.printMessage(inventoryStatus.message());
    }

    public void buy() {
        viewer.promptMessage(PromptMessage.BUY);

    }
}
