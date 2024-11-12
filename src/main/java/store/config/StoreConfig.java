package store.config;

import store.controller.PurchaseController;
import store.controller.StoreController;
import store.domain.OrderInfos;
import store.repository.OrderVerificationRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.repository.SingleRepository;
import store.repository.impl.OrderVerificationRepositoryImpl;
import store.repository.impl.ProductRepositoryImpl;
import store.repository.impl.PromotionRepositoryImpl;
import store.repository.impl.PurchaseInfosRepository;
import store.service.PurchaseService;
import store.service.StoreService;
import store.service.impl.ClearServiceImpl;
import store.service.impl.PurchaseServiceImpl;
import store.service.impl.StoreServiceImpl;
import store.utils.FileHandler;
import store.viewer.InputViewer;
import store.viewer.OutputViewer;
import store.viewer.impl.InputViewerImpl;
import store.viewer.impl.OutputViewerImpl;

public class StoreConfig {
    private static final FileHandler fileHandler = new FileHandler();
    private static final InputViewer inputViewer = new InputViewerImpl();
    private static final OutputViewer outputViewer = new OutputViewerImpl();

    private static final ProductRepository productRepository = new ProductRepositoryImpl();
    private static final PromotionRepository PROMOTION_PROMOTION_REPOSITORY = new PromotionRepositoryImpl();
    private static final SingleRepository<OrderInfos> purchaseInfosRepository = new PurchaseInfosRepository();
    private static final OrderVerificationRepository orderVerificationRepository = new OrderVerificationRepositoryImpl();

    private static final store.service.ClearService clearService = new ClearServiceImpl(productRepository,
            orderVerificationRepository, PROMOTION_PROMOTION_REPOSITORY);

    private static final StoreService storeService = new StoreServiceImpl(productRepository,
            PROMOTION_PROMOTION_REPOSITORY,
            fileHandler);
    private static final StoreController storeController = new StoreController(storeService, clearService,
            outputViewer);

    private static final PurchaseService purchaseService = new PurchaseServiceImpl(purchaseInfosRepository,
            productRepository, orderVerificationRepository);
    private static final PurchaseController purchaseController = new PurchaseController(purchaseService, inputViewer,
            outputViewer);

    private StoreConfig() {
    }

    public static OutputViewer getOutputViewer() {
        return outputViewer;
    }

    public static StoreService getStoreService() {
        return storeService;
    }

    public static store.service.ClearService getClearService() {
        return clearService;
    }

    public static StoreController getStoreController() {
        return storeController;
    }

    public static PurchaseController getPurchaseController() {
        return purchaseController;
    }
}
