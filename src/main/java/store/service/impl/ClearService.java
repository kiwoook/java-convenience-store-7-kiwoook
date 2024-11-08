package store.service.impl;

import store.domain.OrderVerification;
import store.domain.Product;
import store.domain.Promotion;
import store.repository.ListRepository;
import store.repository.MapRepository;

public class ClearService implements store.service.ClearService {

    private final MapRepository<Product> productMapRepository;
    private final ListRepository<OrderVerification> orderVerificationRepository;
    private final MapRepository<Promotion> promotionMapRepository;

    public ClearService(MapRepository<Product> productMapRepository,
                        ListRepository<OrderVerification> orderVerificationRepository,
                        MapRepository<Promotion> promotionMapRepository) {
        this.productMapRepository = productMapRepository;
        this.orderVerificationRepository = orderVerificationRepository;
        this.promotionMapRepository = promotionMapRepository;
    }

    public void clearFile() {
        promotionMapRepository.clear();
        productMapRepository.clear();
    }

    public void clearOrder() {
        orderVerificationRepository.clear();
    }
}
