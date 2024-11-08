package store.config;

import store.controller.PurchaseController;
import store.controller.StoreController;
import store.domain.OrderInfos;
import store.domain.OrderVerification;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ListRepository;
import store.repository.MapRepository;
import store.repository.SingleRepository;
import store.repository.impl.ProductMapRepository;
import store.repository.impl.PromotionMapRepository;
import store.repository.impl.PurchaseInfosRepository;
import store.repository.impl.PurchaseVerificationRepository;
import store.service.PurchaseService;
import store.service.StoreService;
import store.service.impl.ClearService;
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

    private static final MapRepository<Product> productMapRepository = new ProductMapRepository();
    private static final MapRepository<Promotion> promotionMapRepository = new PromotionMapRepository();
    private static final SingleRepository<OrderInfos> purchaseInfosRepository = new PurchaseInfosRepository();
    private static final ListRepository<OrderVerification> purchaseVerificationRepository = new PurchaseVerificationRepository();

    private static final ClearService clearService = new ClearService(productMapRepository,
            purchaseVerificationRepository, promotionMapRepository);

    private static final StoreService storeService = new StoreServiceImpl(productMapRepository,
            promotionMapRepository,
            fileHandler);
    private static final StoreController storeController = new StoreController(storeService, clearService, input_viewer,
            output_viewer);

    private static final PurchaseService purchaseService = new PurchaseServiceImpl(purchaseInfosRepository,
            productMapRepository, purchaseVerificationRepository);
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

    public static ClearService getClearService() {
        return clearService;
    }

    public static StoreController getStoreController() {
        return storeController;
    }

    public static PurchaseController getPurchaseController() {
        return purchaseController;
    }
}
