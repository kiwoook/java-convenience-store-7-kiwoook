package store.service.impl;

import static store.enums.ErrorMessage.NOT_EXIST_PRODUCT;
import static store.enums.ErrorMessage.NOT_SAVE_PURCHASE_INFO;

import java.util.List;
import store.domain.OrderInfo;
import store.domain.OrderInfos;
import store.domain.VerifiedOrder;
import store.domain.VerifiedOrders;
import store.domain.Product;
import store.domain.Receipt;
import store.domain.vo.ProductName;
import store.dto.Message;
import store.dto.OrderConfirmDto;
import store.dto.OrderConfirmDtos;
import store.enums.Confirmation;
import store.exception.EntityNotFoundException;
import store.repository.OrderVerificationRepository;
import store.repository.ProductRepository;
import store.repository.SingleRepository;
import store.service.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {

    private final SingleRepository<OrderInfos> orderInfosRepository;
    private final ProductRepository productRepository;
    private final OrderVerificationRepository orderVerificationRepository;

    public PurchaseServiceImpl(SingleRepository<OrderInfos> orderInfosRepository, ProductRepository productRepository,
                               OrderVerificationRepository orderVerificationRepository) {
        this.orderInfosRepository = orderInfosRepository;
        this.productRepository = productRepository;
        this.orderVerificationRepository = orderVerificationRepository;
    }

    @Override
    public void createOrderInfos(String input) {
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
        Product product = getProduct(orderConfirmDto.name());
        VerifiedOrder verifiedOrder = VerifiedOrder.of(product, orderConfirmDto.requestQuantity());

        orderVerificationRepository.save(verifiedOrder);
    }

    @Override
    public void processProblemQuantity(OrderConfirmDto orderConfirmDto, Confirmation confirmation) {
        Product product = getProduct(orderConfirmDto.name());
        VerifiedOrder verifiedOrder = VerifiedOrder.of(product, orderConfirmDto, confirmation);

        orderVerificationRepository.save(verifiedOrder);
    }


    @Override
    public Message getReceipt(Confirmation membershipConfirmation) {
        VerifiedOrders verifiedOrders = orderVerificationRepository.getAll();
        Receipt receipt = Receipt.from(verifiedOrders);
        Message message = receipt.toMessage(membershipConfirmation);

        verifiedOrders.apply();

        return message;
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
}
