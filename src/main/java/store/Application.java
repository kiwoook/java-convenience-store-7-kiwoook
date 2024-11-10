package store;

import static store.enums.ErrorMessage.INVALID_INPUT;

import camp.nextstep.edu.missionutils.Console;
import java.io.IOException;
import store.config.StoreConfig;
import store.controller.PurchaseController;
import store.controller.StoreController;

public class Application {

    private static final StoreController storeController = StoreConfig.getStoreController();
    private static final PurchaseController purchaseController = StoreConfig.getPurchaseController();

    public static void main(String[] args) {
        init();
        try {
            execute();
        } finally {
            Console.close();
            storeController.clearFile();
        }
    }

    private static void execute() {
        boolean isRetry = true;
        while (isRetry) {
            storeController.explain();
            purchaseController.purchase();
            storeController.clearOrder();
            isRetry = purchaseController.retry();
        }
    }

    private static void init() {
        try {
            storeController.init();
        } catch (IOException e) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
    }
}
