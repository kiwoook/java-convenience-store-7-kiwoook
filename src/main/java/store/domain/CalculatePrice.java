package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.ENTER;
import static store.utils.Constants.TAB;

import java.util.StringJoiner;
import store.enums.Confirmation;
import store.utils.StringFormat;

public class CalculatePrice {

    private static final long MAX_PROMOTION_DISCOUNT = 8000;
    private static final double DISCOUNT_RATE = 0.3;
    private static final long ROUNDING_SCALE = 10;

    private long totalCount;
    private long totalPrice;
    private long totalPromotionDiscount;
    private long totalOriginalPrice;

    public CalculatePrice() {
        this.totalPrice = 0;
        this.totalPromotionDiscount = 0;
        this.totalOriginalPrice = 0;
        this.totalCount = 0;
    }

    public static CalculatePrice create() {
        return new CalculatePrice();
    }

    public void calculate(OrderVerification verification, Product product) {
        this.totalCount += verification.getRequestQuantity();
        this.totalPrice += verification.totalPrice(product);
        this.totalPromotionDiscount += verification.totalDiscount(product);
        this.totalOriginalPrice += verification.totalOriginalPrice(product);
    }

    public String toMessage(Confirmation confirmation) {
        StringJoiner joiner = new StringJoiner(ENTER);

        return joiner.add("====================================")
                .add(toTotal())
                .add(toPromotionDiscount())
                .add(toMembershipDiscount(confirmation))
                .add(toFinalPrice(confirmation))
                .toString();
    }

    private String toFinalPrice(Confirmation confirmation) {
        StringJoiner joiner = new StringJoiner(TAB);
        return joiner.add("내실돈")
                .add(BLANK).add(BLANK).add(BLANK)
                .add(StringFormat.number(
                        totalPrice - totalPromotionDiscount - calculateMembershipDiscount(confirmation)))
                .toString();
    }


    private long calculateMembershipDiscount(Confirmation confirmation) {
        if (confirmation.equals(Confirmation.NO)) {
            return 0L;
        }
        long membershipDiscount = Math.round(totalOriginalPrice * DISCOUNT_RATE * ROUNDING_SCALE) / ROUNDING_SCALE;
        return Math.min(membershipDiscount, MAX_PROMOTION_DISCOUNT);
    }


    private String toTotal() {
        return new StringJoiner(TAB)
                .add("총구매액")
                .add(BLANK)
                .add(StringFormat.number(this.totalCount))
                .add(StringFormat.number(this.totalPrice))
                .toString();
    }

    private String toPromotionDiscount() {
        return new StringJoiner(TAB)
                .add("행사할인")
                .add(BLANK).add(BLANK)
                .add("-" + StringFormat.number(this.totalPromotionDiscount))
                .toString();
    }

    private String toMembershipDiscount(Confirmation confirmation) {
        return new StringJoiner(TAB)
                .add("멤버십 할인")
                .add(BLANK).add(BLANK)
                .add("-" + StringFormat.number(calculateMembershipDiscount(confirmation)))
                .toString();
    }
}
