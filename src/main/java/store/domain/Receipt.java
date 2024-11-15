package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.ENTER;
import static store.utils.Constants.TAB;

import java.util.StringJoiner;
import store.dto.Message;
import store.enums.Confirmation;
import store.utils.StringUtils;

public class Receipt {

    private static final String STORE_TITLE = "==============W 편의점================";
    private static final String ATTRIBUTE = "상품명" + TAB.repeat(2) + "수량" + TAB + "금액";
    private static final String GIFT_TITLE = "=============증" + TAB + "정===============";
    private static final String DIVIDER = "====================================";

    private static final long MAX_PROMOTION_DISCOUNT = 8000;
    private static final double DISCOUNT_RATE = 0.3;
    private static final long ROUNDING_SCALE = 10;

    private final VerifiedOrders verifiedOrders;

    public Receipt(VerifiedOrders verifiedOrders) {
        this.verifiedOrders = verifiedOrders;
    }

    public static Receipt from(VerifiedOrders verifiedOrders) {
        return new Receipt(verifiedOrders);
    }

    public Message toMessage(Confirmation confirmation) {
        StringJoiner joiner = receiptTitle();
        String message = joiner.add(verifiedOrders.getOrderStatus())
                .add(GIFT_TITLE)
                .add(verifiedOrders.getDiscountStatus())
                .add(toCalculateMessage(confirmation))
                .toString();

        return new Message(message);
    }

    public String toCalculateMessage(Confirmation confirmation) {
        return new StringJoiner(ENTER)
                .add(DIVIDER)
                .add(toTotal())
                .add(toPromotionDiscount())
                .add(toMembershipDiscount(confirmation))
                .add(toFinalPrice(confirmation))
                .toString();
    }

    private StringJoiner receiptTitle() {
        return new StringJoiner(ENTER)
                .add(BLANK)
                .add(STORE_TITLE)
                .add(ATTRIBUTE);
    }

    protected long calculateMembershipDiscount(Confirmation confirmation) {
        if (confirmation.equals(Confirmation.NO)) {
            return 0L;
        }
        long totalOriginalPrice = verifiedOrders.getTotalOriginalPrice();
        long membershipDiscount = Math.round(totalOriginalPrice * DISCOUNT_RATE * ROUNDING_SCALE) / ROUNDING_SCALE;
        return Math.min(membershipDiscount, MAX_PROMOTION_DISCOUNT);
    }

    private String toTotal() {
        long totalCount = verifiedOrders.getTotalCount();
        long totalPrice = verifiedOrders.getTotalPrice();
        return new StringJoiner(TAB)
                .add("총구매액").add(BLANK)
                .add(StringUtils.numberFormat(totalCount))
                .add(StringUtils.numberFormat(totalPrice))
                .toString();
    }

    private String toMembershipDiscount(Confirmation confirmation) {
        return new StringJoiner(TAB)
                .add("멤버십할인")
                .add(BLANK).add(BLANK)
                .add("-" + StringUtils.numberFormat(calculateMembershipDiscount(confirmation)))
                .toString();
    }

    private String toPromotionDiscount() {
        long totalPromotionDiscount = verifiedOrders.getTotalDiscount();
        return new StringJoiner(TAB)
                .add("행사할인")
                .add(BLANK).add(BLANK)
                .add("-" + StringUtils.numberFormat(totalPromotionDiscount))
                .toString();
    }

    private String toFinalPrice(Confirmation confirmation) {
        long totalPrice = verifiedOrders.getTotalPrice();
        long totalPromotionDiscount = verifiedOrders.getTotalDiscount();
        return new StringJoiner(TAB)
                .add("내실돈")
                .add(BLANK).add(BLANK).add(BLANK)
                .add(StringUtils.numberFormat(
                        totalPrice - totalPromotionDiscount - calculateMembershipDiscount(confirmation)))
                .toString();
    }


}
