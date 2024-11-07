package store.service.impl;

import static store.enums.ErrorMessage.NOT_EXIST_PRODUCT;
import static store.enums.ErrorMessage.NOT_SAVE_PURCHASE_INFO;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.PurchaseInfos;
import store.domain.PurchaseVerification;
import store.dto.PurchaseConfirmDto;
import store.dto.PurchaseConfirmDtos;
import store.dto.ReceiptDto;
import store.enums.Confirmation;
import store.exception.EntityNotFoundException;
import store.repository.ListRepository;
import store.repository.MapRepository;
import store.repository.SingleRepository;
import store.service.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {

    private final SingleRepository<PurchaseInfos> purchaseInfosRepository;
    private final MapRepository<Product> productMapRepository;
    private final ListRepository<PurchaseVerification> purchaseVerificationRepository;

    public PurchaseServiceImpl(SingleRepository<PurchaseInfos> purchaseInfosRepository,
                               MapRepository<Product> productMapRepository,
                               ListRepository<PurchaseVerification> purchaseVerificationRepository) {
        this.purchaseInfosRepository = purchaseInfosRepository;
        this.productMapRepository = productMapRepository;
        this.purchaseVerificationRepository = purchaseVerificationRepository;
    }

    @Override
    public void create(String input) {
        PurchaseInfos purchaseInfos = PurchaseInfos.create(input);
        validateQuantity(purchaseInfos);
        purchaseInfosRepository.save(purchaseInfos);
    }

    @Override
    public PurchaseConfirmDtos check() {
        PurchaseInfos purchaseInfos = purchaseInfosRepository.get()
                .orElseThrow(() -> new EntityNotFoundException(NOT_SAVE_PURCHASE_INFO.getMessage()));

        List<PurchaseConfirmDto> purchaseConfirmDtoList = new ArrayList<>();
        purchaseInfos.forEach(purchaseInfo -> {
            Product product = getProduct(purchaseInfo.toString());
            PurchaseConfirmDto confirmDto = purchaseInfo.toConfirmDto(product);
            purchaseConfirmDtoList.add(confirmDto);
        });

        return PurchaseConfirmDtos.create(purchaseConfirmDtoList);
    }

    @Override
    public void processQuantity(PurchaseConfirmDto purchaseConfirmDto) {
        PurchaseVerification verification = PurchaseVerification.from(purchaseConfirmDto);

        purchaseVerificationRepository.save(verification);
    }


    @Override
    public void processProblemQuantity(PurchaseConfirmDto purchaseConfirmDto, Confirmation confirmation) {
        PurchaseVerification verification = PurchaseVerification.of(purchaseConfirmDto, confirmation);

        purchaseVerificationRepository.save(verification);
    }


    @Override
    public ReceiptDto getReceipt(Confirmation membershipConfirmation) {
        List<PurchaseVerification> purchaseVerifications = purchaseVerificationRepository.getAll();

        purchaseVerifications.forEach(purchaseVerification -> {
            Product product = getProduct(purchaseVerification.toString());

        });

        return null;
    }

    protected void validateQuantity(PurchaseInfos purchaseInfos) {
        purchaseInfos.forEach(purchaseInfo -> {
            Product product = getProduct(purchaseInfo.toString());

            purchaseInfo.validQuantity(product);
        });
    }

    private Product getProduct(String productName) {
        return productMapRepository.findById(productName)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PRODUCT.getMessage()));
    }
}
