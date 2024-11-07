package store;

import java.io.IOException;
import store.config.StoreConfig;
import store.controller.PurchaseController;
import store.controller.StoreController;

public class Application {

    private static final StoreController storeController = StoreConfig.getStoreController();
    private static final PurchaseController purchaseController = StoreConfig.getPurchaseController();

    public static void main(String[] args) throws IOException {
        storeController.init();
        storeController.explain();

        purchaseController.buy();
    }
}
