package store;

import store.controller.StoreController;
import store.utils.FileHandler;
import store.view.InputViewer;
import store.view.OutputViewer;

public class Application {
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        InputViewer inputViewer = new InputViewer();
        OutputViewer outputViewer = new OutputViewer();

        StoreController storeController = new StoreController(fileHandler, inputViewer, outputViewer);

        storeController.init();
        storeController.execute();
    }
}
