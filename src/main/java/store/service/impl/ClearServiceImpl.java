package store.service.impl;

import store.domain.OrderVerification;
import store.domain.Promotion;
import store.repository.ListRepository;
import store.repository.PromotionRepository;
import store.repository.ProductRepository;
import store.service.ClearService;

public class ClearServiceImpl implements ClearService {

    private final ProductRepository productRepository;
    private final ListRepository<OrderVerification> orderVerificationRepository;
    private final PromotionRepository promotionPromotionRepository;

    public ClearServiceImpl(ProductRepository productRepository,
                            ListRepository<OrderVerification> orderVerificationRepository,
                            PromotionRepository promotionPromotionRepository) {
        this.productRepository = productRepository;
        this.orderVerificationRepository = orderVerificationRepository;
        this.promotionPromotionRepository = promotionPromotionRepository;
    }

    public void clearFile() {
        promotionPromotionRepository.clear();
        productRepository.clear();
    }

    public void clearOrder() {
        orderVerificationRepository.clear();
    }
}
