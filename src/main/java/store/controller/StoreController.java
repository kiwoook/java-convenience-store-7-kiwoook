package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.dto.ProductConfirmDto;
import store.dto.ProductDto;
import store.dto.PromotionDto;
import store.model.Confirmation;
import store.model.Product;
import store.model.Products;
import store.model.Promotion;
import store.model.Promotions;
import store.model.PurchaseInfo;
import store.model.PurchaseInfos;
import store.model.Receipt;
import store.model.VerifiedOrders;
import store.utils.ErrorMessage;
import store.utils.FileHandler;
import store.utils.RecoveryUtils;
import store.view.InputViewer;
import store.view.OutputViewer;

public class StoreController {

    private static final String NULL = "null";

    private final FileHandler fileHandler;
    private final InputViewer inputViewer;
    private final OutputViewer outputViewer;

    private final Promotions promotions = Promotions.create();
    private final Products products = Products.create();
    private final VerifiedOrders verifiedOrders = VerifiedOrders.create();

    public StoreController(FileHandler fileHandler, InputViewer inputViewer, OutputViewer outputViewer) {
        this.fileHandler = fileHandler;
        this.inputViewer = inputViewer;
        this.outputViewer = outputViewer;
    }

    public void init() {
        List<PromotionDto> promotionDtos = fileHandler.readPromotionDto();
        savePromotions(promotionDtos);
        List<ProductDto> productDtos = fileHandler.readProductFile();
        saveProduct(productDtos);
    }

    public void execute() {
        Confirmation retry;
        do {
            String productStatus = products.statusAll();
            PurchaseInfos allPurchaseInfo = getAllPurchaseInfo(productStatus);
            checkProblemQuantity(productStatus, allPurchaseInfo);
            printReceipt(confirmMembership());

            verifiedOrders.apply();
            verifiedOrders.clear();

            retry = inputViewer.retryPurchase();
        } while (retry.equals(Confirmation.YES));
    }

    public Confirmation confirmMembership() {
        return RecoveryUtils.executeWithRetry(inputViewer::confirmMembership);
    }

    private void printReceipt(Confirmation confirmMembership) {
        Receipt receipt = Receipt.from(verifiedOrders);

        outputViewer.printMessage(receipt.toMessage(confirmMembership));
    }

    private PurchaseInfos getAllPurchaseInfo(String productStatus) {
        return RecoveryUtils.executeWithRetry(
                () -> PurchaseInfos.from(inputViewer.requestProduct(productStatus)));
    }

    private void checkProblemQuantity(String productStatus, PurchaseInfos purchaseInfos) {
        try {
            List<ProductConfirmDto> confirmDtos = new ArrayList<>();
            for (PurchaseInfo purchaseInfo : purchaseInfos.items()) {
                long problemQuantity = getProduct(purchaseInfo.productName())
                        .checkProblemQuantity(purchaseInfo.requestQuantity());

                confirmDtos.add(new ProductConfirmDto(purchaseInfo.productName(), purchaseInfo.requestQuantity(),
                        problemQuantity));
            }
            confirmRequestProduct(confirmDtos);
        } catch (IllegalArgumentException e) {
            outputViewer.printError(e);
            getAllPurchaseInfo(productStatus);
        }
    }

    private void confirmRequestProduct(List<ProductConfirmDto> confirmDtos) {
        for (ProductConfirmDto confirmDto : confirmDtos) {
            Product product = getProduct(confirmDto.productName());

            if (confirmDto.problemQuantity() == 0) {
                verifiedOrders.addProduct(product, confirmDto.requestQuantity());
            }
            if (confirmDto.problemQuantity() > 0) {
                checkGiftQuantity(confirmDto);
            }
            if (confirmDto.problemQuantity() < 0) {
                checkOriginalPriceQuantity(confirmDto);
            }
        }
    }

    private void checkOriginalPriceQuantity(ProductConfirmDto confirmDto) {
        Confirmation confirmation = RecoveryUtils.executeWithRetry(
                () -> inputViewer.checkOriginalPriceQuantity(confirmDto.productName(), confirmDto.problemQuantity()));

        Product product = getProduct(confirmDto.productName());

        verifiedOrders.addProductByOriginalPriceQuantity(product, confirmDto.requestQuantity(),
                confirmDto.problemQuantity(), confirmation);
    }

    private void checkGiftQuantity(ProductConfirmDto confirmDto) {
        Confirmation confirmation = RecoveryUtils.executeWithRetry(
                () -> inputViewer.checkGiftQuantity(confirmDto.productName()));

        Product product = getProduct(confirmDto.productName());

        verifiedOrders.addProductByGiftQuantity(product, confirmDto.requestQuantity(), confirmDto.problemQuantity(),
                confirmation);
    }

    private Product getProduct(String productName) {
        return products.get(productName)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NOT_FOUND_PRODUCT.getMessage()));
    }


    private void saveProduct(List<ProductDto> productDtos) {
        for (ProductDto productDto : productDtos) {
            Promotion promotion = null;
            if (!productDto.promotionName().equals(NULL)) {
                promotion = promotions.get(productDto.promotionName());
                if (promotion == null) {
                    continue;
                }
            }

            products.put(productDto.name(), productDto.price(), productDto.quantity(), promotion);
        }
    }

    private void savePromotions(List<PromotionDto> promotionDtos) {
        for (PromotionDto promotionDto : promotionDtos) {
            LocalDate now = LocalDate.from(DateTimes.now());

            Promotion promotion = new Promotion(
                    promotionDto.name(),
                    promotionDto.buy());

            if (promotions.isValidDate(now, promotionDto.startDate(), promotionDto.endDate())) {
                promotions.put(promotionDto.name(), promotion);
            }
        }
    }


}
