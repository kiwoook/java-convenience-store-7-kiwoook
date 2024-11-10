package store.service.impl;

import store.repository.OrderVerificationRepository;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.ClearService;

public class ClearServiceImpl implements ClearService {

    private final ProductRepository productRepository;
    private final OrderVerificationRepository orderVerificationRepository;
    private final PromotionRepository promotionPromotionRepository;

    public ClearServiceImpl(ProductRepository productRepository,
                            OrderVerificationRepository orderVerificationRepository,
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
