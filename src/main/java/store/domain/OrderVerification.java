package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.TAB;

import java.util.StringJoiner;
import store.dto.OrderConfirmDto;
import store.enums.Confirmation;
import store.utils.StringUtils;

public record OrderVerification(String productName, long requestQuantity) {

    private static final long ZERO = 0;

    public static OrderVerification from(OrderConfirmDto confirmDto) {
        return new OrderVerification(confirmDto.name(), confirmDto.requestQuantity());
    }

    public static OrderVerification of(OrderConfirmDto confirmDto, Confirmation confirmation) {
        return new OrderVerification(confirmDto.name(), processQuantity(confirmDto, confirmation));
    }

    public static long processQuantity(OrderConfirmDto confirmDto, Confirmation confirmation) {
        if (confirmDto.problemQuantity() > 0 && confirmation.equals(Confirmation.YES)) {
            return confirmDto.requestQuantity() + confirmDto.problemQuantity();
        }
        if (confirmDto.problemQuantity() < 0 && confirmation.equals(Confirmation.NO)) {
            return ZERO;
        }

        return confirmDto.requestQuantity();
    }

    public String totalPriceByProduct(Product product) {
        long totalPrice = product.calculateSumPrice(requestQuantity);
        return StringUtils.numberFormat(totalPrice);
    }

    public String toQuantityString() {
        return String.valueOf(requestQuantity);
    }


    public String getStatus(Product product) {
        StringJoiner joiner = new StringJoiner(TAB);

        return joiner.add(productName)
                .add(BLANK)
                .add(toQuantityString())
                .add(BLANK)
                .add(totalPriceByProduct(product))
                .toString();
    }

    public String getDiscountStatus(Product product) {
        StringJoiner joiner = new StringJoiner(TAB);
        long giftQuantity = product.calculateGiftQuantity(requestQuantity);

        if (giftQuantity == 0) {
            return null;
        }

        return joiner.add(productName + TAB)
                .add(String.valueOf(giftQuantity))
                .toString();
    }

    public long totalOriginalPrice(Product product) {
        return product.sumOriginalPrice(requestQuantity);
    }

    public long totalPrice(Product product) {
        return product.calculateSumPrice(requestQuantity);
    }

    public long totalDiscount(Product product) {
        long giftQuantity = product.calculateGiftQuantity(requestQuantity);

        return product.calculateSumPrice(giftQuantity);
    }

    public void apply(Product product) {
        product.applyStock(requestQuantity);
    }

    @Override
    public String toString() {
        return productName;
    }

}
