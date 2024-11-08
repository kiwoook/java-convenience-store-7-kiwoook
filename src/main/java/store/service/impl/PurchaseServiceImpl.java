package store.service.impl;

import static store.enums.ErrorMessage.NOT_EXIST_PRODUCT;
import static store.enums.ErrorMessage.NOT_SAVE_PURCHASE_INFO;

import java.util.ArrayList;
import java.util.List;
import store.domain.OrderInfos;
import store.domain.OrderVerification;
import store.domain.Product;
import store.domain.Receipt;
import store.dto.Message;
import store.dto.OrderConfirmDto;
import store.dto.OrderConfirmDtos;
import store.enums.Confirmation;
import store.exception.EntityNotFoundException;
import store.repository.ListRepository;
import store.repository.MapRepository;
import store.repository.SingleRepository;
import store.service.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {

    private final SingleRepository<OrderInfos> purchaseInfosRepository;
    private final MapRepository<Product> productMapRepository;
    private final ListRepository<OrderVerification> purchaseVerificationRepository;

    public PurchaseServiceImpl(SingleRepository<OrderInfos> purchaseInfosRepository,
                               MapRepository<Product> productMapRepository,
                               ListRepository<OrderVerification> purchaseVerificationRepository) {
        this.purchaseInfosRepository = purchaseInfosRepository;
        this.productMapRepository = productMapRepository;
        this.purchaseVerificationRepository = purchaseVerificationRepository;
    }

    @Override
    public void create(String input) {
        OrderInfos purchaseInfos = OrderInfos.create(input);
        validateQuantity(purchaseInfos);
        purchaseInfosRepository.save(purchaseInfos);
    }

    @Override
    public OrderConfirmDtos check() {
        OrderInfos purchaseInfos = purchaseInfosRepository.get()
                .orElseThrow(() -> new EntityNotFoundException(NOT_SAVE_PURCHASE_INFO.getMessage()));

        List<OrderConfirmDto> orderConfirmDtoList = new ArrayList<>();
        purchaseInfos.forEach(purchaseInfo -> {
            Product product = getProduct(purchaseInfo.toString());
            OrderConfirmDto confirmDto = purchaseInfo.toConfirmDto(product);
            orderConfirmDtoList.add(confirmDto);
        });

        return OrderConfirmDtos.create(orderConfirmDtoList);
    }

    @Override
    public void processQuantity(OrderConfirmDto orderConfirmDto) {
        OrderVerification verification = OrderVerification.from(orderConfirmDto);

        purchaseVerificationRepository.save(verification);
    }


    @Override
    public void processProblemQuantity(OrderConfirmDto orderConfirmDto, Confirmation confirmation) {
        OrderVerification verification = OrderVerification.of(orderConfirmDto, confirmation);

        purchaseVerificationRepository.save(verification);
    }


    @Override
    public Message getReceipt(Confirmation membershipConfirmation) {
        List<OrderVerification> orderVerifications = purchaseVerificationRepository.getAll();
        Receipt receipt = Receipt.create();

        orderVerifications.forEach(purchaseVerification -> {
            Product product = getProduct(purchaseVerification.toString());
            receipt.processOrder(purchaseVerification, product);
            purchaseVerification.apply(product);

            productMapRepository.save(purchaseVerification.toString(), product);
        });

        return receipt.toMessage(membershipConfirmation);
    }

    protected void validateQuantity(OrderInfos purchaseInfos) {
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
