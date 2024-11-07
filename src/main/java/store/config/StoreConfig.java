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
import store.viewer.Viewer;
import store.viewer.impl.ViewerImpl;

public class StoreConfig {
    private static final FileHandler fileHandler = new FileHandler();
    private static final Viewer viewer = new ViewerImpl();

    private static final MapRepository<Product> productRepository = new ProductRepository();
    private static final MapRepository<Promotion> promotionRepository = new PromotionRepository();

    private static final StoreService storeService = new StoreServiceImpl(productRepository, promotionRepository,
            fileHandler);

    private static final StoreController storeController = new StoreController(storeService, viewer);

    private StoreConfig() {
    }

    public static StoreService getStoreService() {
        return storeService;
    }

    public static StoreController getStoreController() {
        return storeController;
    }

    public static Viewer getViewer() {
        return viewer;
    }
}
