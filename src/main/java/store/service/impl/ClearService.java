package store.service.impl;

import store.domain.OrderVerification;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ListRepository;
import store.repository.MapRepository;

public class ClearService {

    private final MapRepository<Product> productMapRepository;
    private final ListRepository<OrderVerification> purchaseVerificationRepository;
    private final MapRepository<Promotion> promotionMapRepository;

    public ClearService(MapRepository<Product> productMapRepository,
                        ListRepository<OrderVerification> purchaseVerificationRepository,
                        MapRepository<Promotion> promotionMapRepository) {
        this.productMapRepository = productMapRepository;
        this.purchaseVerificationRepository = purchaseVerificationRepository;
        this.promotionMapRepository = promotionMapRepository;
    }

    public void clearFile() {
        promotionMapRepository.clear();
        productMapRepository.clear();
    }

    public void clearOrder() {
        purchaseVerificationRepository.clear();
    }
}
