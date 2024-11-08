package store.service.impl;

import static store.enums.ErrorMessage.NOT_EXIST_PRODUCT;
import static store.enums.ErrorMessage.NOT_SAVE_PURCHASE_INFO;

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

    private final SingleRepository<OrderInfos> orderInfosRepository;
    private final MapRepository<Product> productMapRepository;
    private final ListRepository<OrderVerification> orderVerificationRepository;

    public PurchaseServiceImpl(SingleRepository<OrderInfos> orderInfosRepository,
                               MapRepository<Product> productMapRepository,
                               ListRepository<OrderVerification> orderVerificationRepository) {
        this.orderInfosRepository = orderInfosRepository;
        this.productMapRepository = productMapRepository;
        this.orderVerificationRepository = orderVerificationRepository;
    }

    @Override
    public void create(String input) {
        OrderInfos purchaseInfos = OrderInfos.create(input);
        validateQuantity(purchaseInfos);
        orderInfosRepository.save(purchaseInfos);
    }

    @Override
    public OrderConfirmDtos check() {
        OrderInfos purchaseInfos = getOrderInfos();

        List<OrderConfirmDto> orderConfirmDtoList = purchaseInfos.stream()
                .map(purchaseInfo -> {
                    Product product = getProduct(purchaseInfo.getProductName());
                    return purchaseInfo.toConfirmDto(product);
                })
                .toList();

        return OrderConfirmDtos.create(orderConfirmDtoList);
    }

    @Override
    public void processQuantity(OrderConfirmDto orderConfirmDto) {
        OrderVerification verification = OrderVerification.from(orderConfirmDto);

        orderVerificationRepository.save(verification);
    }


    @Override
    public void processProblemQuantity(OrderConfirmDto orderConfirmDto, Confirmation confirmation) {
        OrderVerification verification = OrderVerification.of(orderConfirmDto, confirmation);

        orderVerificationRepository.save(verification);
    }


    @Override
    public Message getReceipt(Confirmation membershipConfirmation) {
        List<OrderVerification> orderVerifications = orderVerificationRepository.getAll();
        Receipt receipt = Receipt.create();

        orderVerifications.forEach(purchaseVerification -> {
            Product product = getProduct(purchaseVerification.productName());
            receipt.processOrder(purchaseVerification, product);
            applyPurchase(product, purchaseVerification);
        });

        return receipt.toMessage(membershipConfirmation);
    }

    protected void validateQuantity(OrderInfos orderInfos) {
        orderInfos.forEach(orderInfo -> {
            Product product = getProduct(orderInfo.getProductName());
            orderInfo.validQuantity(product);
        });
    }

    private OrderInfos getOrderInfos() {
        return orderInfosRepository.get()
                .orElseThrow(() -> new EntityNotFoundException(NOT_SAVE_PURCHASE_INFO.getMessage()));
    }

    private Product getProduct(String productName) {
        return productMapRepository.findById(productName)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PRODUCT.getMessage()));
    }

    private void applyPurchase(Product product, OrderVerification purchaseVerification) {
        purchaseVerification.apply(product);
        productMapRepository.save(purchaseVerification.productName(), product);
    }
}
