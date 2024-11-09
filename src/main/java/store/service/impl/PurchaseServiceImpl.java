package store.service.impl;

import static store.enums.ErrorMessage.NOT_EXIST_PRODUCT;
import static store.enums.ErrorMessage.NOT_SAVE_PURCHASE_INFO;

import java.util.List;
import store.domain.OrderInfo;
import store.domain.OrderInfos;
import store.domain.OrderVerification;
import store.domain.Product;
import store.domain.Receipt;
import store.domain.vo.ProductName;
import store.dto.Message;
import store.dto.OrderConfirmDto;
import store.dto.OrderConfirmDtos;
import store.enums.Confirmation;
import store.exception.EntityNotFoundException;
import store.repository.ListRepository;
import store.repository.ProductRepository;
import store.repository.SingleRepository;
import store.service.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {

    private final SingleRepository<OrderInfos> orderInfosRepository;
    private final ProductRepository productRepository;
    private final ListRepository<OrderVerification> orderVerificationRepository;

    public PurchaseServiceImpl(SingleRepository<OrderInfos> orderInfosRepository, ProductRepository productRepository,
                               ListRepository<OrderVerification> orderVerificationRepository) {
        this.orderInfosRepository = orderInfosRepository;
        this.productRepository = productRepository;
        this.orderVerificationRepository = orderVerificationRepository;
    }

    @Override
    public void create(String input) {
        OrderInfos orderInfos = OrderInfos.create(input);
        validateQuantity(orderInfos);
        orderInfosRepository.save(orderInfos);
    }

    @Override
    public OrderConfirmDtos check() {
        OrderInfos orderInfos = getOrderInfos();

        List<OrderConfirmDto> orderConfirmDtoList = orderInfos.stream()
                .map(this::toOrderConfirmDto)
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
            Product product = getProduct(purchaseVerification.name());
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

    private OrderConfirmDto toOrderConfirmDto(OrderInfo orderInfo) {
        Product product = getProduct(orderInfo.getProductName());
        return orderInfo.toConfirmDto(product);
    }

    private OrderInfos getOrderInfos() {
        return orderInfosRepository.get()
                .orElseThrow(() -> new EntityNotFoundException(NOT_SAVE_PURCHASE_INFO.getMessage()));
    }

    private Product getProduct(ProductName productName) {
        return productRepository.findByProductName(productName)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PRODUCT.getMessage()));
    }

    private void applyPurchase(Product product, OrderVerification purchaseVerification) {
        purchaseVerification.apply(product);
        productRepository.save(purchaseVerification.name(), product);
    }
}
