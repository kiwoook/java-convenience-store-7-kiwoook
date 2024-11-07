package store.service.impl;

import static store.enums.ErrorMessage.NOT_EXIST_PRODUCT;

import store.domain.Product;
import store.domain.PurchaseInfos;
import store.dto.CheckPurchaseInfosDto;
import store.dto.ReceiptDto;
import store.enums.Confirmation;
import store.repository.MapRepository;
import store.repository.SingleRepository;
import store.service.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {

    private final SingleRepository<PurchaseInfos> purchaseInfosRepository;
    private final MapRepository<Product> productRepository;

    public PurchaseServiceImpl(SingleRepository<PurchaseInfos> purchaseInfosRepository,
                               MapRepository<Product> productRepository) {
        this.purchaseInfosRepository = purchaseInfosRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void create(String input) {
        PurchaseInfos purchaseInfos = PurchaseInfos.create(input);
        validateQuantity(purchaseInfos);
        purchaseInfosRepository.save(purchaseInfos);
    }

    @Override
    public CheckPurchaseInfosDto check() {
        return null;
    }

    @Override
    public ReceiptDto getReceipt(CheckPurchaseInfosDto infoDto, Confirmation membershipConfirmation) {
        return null;
    }

    protected void validateQuantity(PurchaseInfos purchaseInfos) {
        purchaseInfos.forEach(purchaseInfo -> {
            Product product = productRepository.findById(purchaseInfo.toString())
                    .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PRODUCT.getMessage()));

            purchaseInfo.validQuantity(product);
        });
    }
}
