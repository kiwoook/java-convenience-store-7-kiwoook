package store.domain;

import static store.utils.Constants.BLANK;
import static store.utils.Constants.ENTER;
import static store.utils.Constants.TAB;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import store.dto.Message;
import store.enums.Confirmation;

public class Receipt {

    private static final String STORE_TITLE = "==============W 편의점================";
    private static final String ATTRIBUTE = "상품명" + TAB.repeat(2) + "수량" + TAB + "금액";
    private static final String GIFT_TITLE = "=============증" + TAB + "정===============";

    private final CalculatePrice calculatePrice;
    private final List<String> orderStatus;
    private final List<String> discountStatus;

    private Receipt() {
        this.calculatePrice = new CalculatePrice();
        this.orderStatus = new ArrayList<>();
        this.discountStatus = new ArrayList<>();
    }

    public static Receipt create(){
        return new Receipt();
    }

    public void processOrder(OrderVerification verification, Product product) {
        orderStatus.add(verification.getStatus(product));
        addDiscountStatus(verification, product);
        calculatePrice.calculate(verification, product);
    }

    private void addDiscountStatus(OrderVerification verification, Product product) {
        String discountStatus = verification.getDiscountStatus(product);

        if (discountStatus != null) {
            this.discountStatus.add(discountStatus);
        }
    }

    public Message toMessage(Confirmation confirmation) {
        StringJoiner joiner = new StringJoiner(ENTER);

        processOrderStatus(joiner);
        processDiscountStatus(joiner);
        joiner.add(calculatePrice.toMessage(confirmation));

        return new Message(joiner.toString());
    }

    private void processOrderStatus(StringJoiner joiner) {
        joiner.add(BLANK)
                .add(STORE_TITLE)
                .add(ATTRIBUTE);

        for (String orderMessage : orderStatus) {
            joiner.add(orderMessage);
        }
    }

    private void processDiscountStatus(StringJoiner joiner) {
        joiner.add(GIFT_TITLE);
        for (String discountMessage : discountStatus) {
            joiner.add(discountMessage);
        }
    }

}
