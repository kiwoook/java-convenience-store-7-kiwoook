package store;

import java.io.IOException;
import store.config.StoreConfig;
import store.controller.StoreController;

public class Application {

    private static final StoreController storeController = StoreConfig.getStoreController();

    public static void main(String[] args) throws IOException {
        storeController.init();
        storeController.explain();
    }
}
