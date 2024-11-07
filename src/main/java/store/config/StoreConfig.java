package store.config;

import store.controller.StoreController;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.MapRepository;
import store.repository.impl.ProductRepository;
import store.repository.impl.PromotionRepository;
import store.service.StoreService;
import store.service.impl.StoreServiceImpl;
import store.utils.FileHandler;
import store.viewer.InputViewer;
import store.viewer.OutputViewer;
import store.viewer.impl.InputViewerImpl;
import store.viewer.impl.OutputViewerImpl;

public class StoreConfig {
    private static final FileHandler fileHandler = new FileHandler();
    private static final InputViewer input_viewer = new InputViewerImpl();
    private static final OutputViewer output_viewer = new OutputViewerImpl();

    private static final MapRepository<Product> productRepository = new ProductRepository();
    private static final MapRepository<Promotion> promotionRepository = new PromotionRepository();

    private static final StoreService storeService = new StoreServiceImpl(productRepository, promotionRepository,
            fileHandler);

    private static final StoreController storeController = new StoreController(storeService, input_viewer,
            output_viewer);

    private StoreConfig() {
    }

    public static StoreService getStoreService() {
        return storeService;
    }

    public static StoreController getStoreController() {
        return storeController;
    }

    public static OutputViewer getOutputViewer() {
        return output_viewer;
    }
}
