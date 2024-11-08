package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.ENTER;
import static store.utils.Constants.TAB;

import java.util.StringJoiner;
import store.enums.Confirmation;
import store.utils.StringUtils;

public class CalculatePrice {

    private static final String DIVIDER = "====================================";

    private static final long MAX_PROMOTION_DISCOUNT = 8000;
    private static final double DISCOUNT_RATE = 0.3;
    private static final long ROUNDING_SCALE = 10;

    private long totalCount;
    private long totalPrice;
    private long totalPromotionDiscount;
    private long totalOriginalPrice;

    protected CalculatePrice(long totalCount, long totalPrice, long totalPromotionDiscount, long totalOriginalPrice) {
        this.totalCount = totalCount;
        this.totalPrice = totalPrice;
        this.totalPromotionDiscount = totalPromotionDiscount;
        this.totalOriginalPrice = totalOriginalPrice;
    }

    private CalculatePrice() {
        this.totalPrice = 0;
        this.totalPromotionDiscount = 0;
        this.totalOriginalPrice = 0;
        this.totalCount = 0;
    }

    public static CalculatePrice create() {
        return new CalculatePrice();
    }

    public void calculate(OrderVerification verification, Product product) {
        this.totalCount += verification.requestQuantity();
        this.totalPrice += verification.totalPrice(product);
        this.totalPromotionDiscount += verification.totalDiscount(product);
        this.totalOriginalPrice += verification.totalOriginalPrice(product);
    }

    public String toMessage(Confirmation confirmation) {
        return new StringJoiner(ENTER)
                .add(DIVIDER)
                .add(toTotal())
                .add(toPromotionDiscount())
                .add(toMembershipDiscount(confirmation))
                .add(toFinalPrice(confirmation))
                .toString();
    }

    protected long calculateMembershipDiscount(Confirmation confirmation) {
        if (confirmation.equals(Confirmation.NO)) {
            return 0L;
        }
        long membershipDiscount = Math.round(totalOriginalPrice * DISCOUNT_RATE * ROUNDING_SCALE) / ROUNDING_SCALE;
        return Math.min(membershipDiscount, MAX_PROMOTION_DISCOUNT);
    }

    private String toFinalPrice(Confirmation confirmation) {
        return new StringJoiner(TAB)
                .add("내실돈")
                .add(BLANK).add(BLANK).add(BLANK)
                .add(StringUtils.numberFormat(
                        totalPrice - totalPromotionDiscount - calculateMembershipDiscount(confirmation)))
                .toString();
    }

    private String toTotal() {
        return new StringJoiner(TAB)
                .add("총구매액")
                .add(BLANK)
                .add(StringUtils.numberFormat(this.totalCount))
                .add(StringUtils.numberFormat(this.totalPrice))
                .toString();
    }

    private String toPromotionDiscount() {
        return new StringJoiner(TAB)
                .add("행사할인")
                .add(BLANK).add(BLANK)
                .add("-" + StringUtils.numberFormat(this.totalPromotionDiscount))
                .toString();
    }

    private String toMembershipDiscount(Confirmation confirmation) {
        return new StringJoiner(TAB)
                .add("멤버십 할인")
                .add(BLANK).add(BLANK)
                .add("-" + StringUtils.numberFormat(calculateMembershipDiscount(confirmation)))
                .toString();
    }
}
