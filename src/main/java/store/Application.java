package store;

import camp.nextstep.edu.missionutils.Console;
import java.io.IOException;
import store.config.StoreConfig;
import store.controller.PurchaseController;
import store.controller.StoreController;

public class Application {

    private static final StoreController storeController = StoreConfig.getStoreController();
    private static final PurchaseController purchaseController = StoreConfig.getPurchaseController();

    public static void main(String[] args) throws IOException {
        storeController.init();

        try {
            boolean isRetry = true;
            while (isRetry) {
                storeController.explain();

                purchaseController.buy();
                purchaseController.check();
                purchaseController.printReceipt();
                storeController.clearOrder();
                isRetry = purchaseController.retry();
            }
        } finally {
            Console.close();
            storeController.clearFile();
        }


    }
}
