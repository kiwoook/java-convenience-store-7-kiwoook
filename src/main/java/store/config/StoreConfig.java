package store.config;

import store.controller.PurchaseController;
import store.controller.StoreController;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PurchaseInfos;
import store.repository.MapRepository;
import store.repository.SingleRepository;
import store.repository.impl.ProductRepository;
import store.repository.impl.PromotionRepository;
import store.repository.impl.PurchaseInfoRepository;
import store.service.PurchaseService;
import store.service.StoreService;
import store.service.impl.PurchaseServiceImpl;
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
    private static final SingleRepository<PurchaseInfos> purchaseInfosRepository = new PurchaseInfoRepository();

    private static final StoreService storeService = new StoreServiceImpl(productRepository, promotionRepository,
            fileHandler);
    private static final StoreController storeController = new StoreController(storeService, input_viewer,
            output_viewer);

    private static final PurchaseService purchaseService = new PurchaseServiceImpl(purchaseInfosRepository,
            productRepository);
    private static final PurchaseController purchaseController = new PurchaseController(purchaseService, input_viewer,
            output_viewer);

    private StoreConfig() {
    }

    public static OutputViewer getOutputViewer() {
        return output_viewer;
    }

    public static StoreService getStoreService() {
        return storeService;
    }

    public static StoreController getStoreController() {
        return storeController;
    }

    public static PurchaseController getPurchaseController() {
        return purchaseController;
    }
}
