package store.model;

import static store.utils.Constants.ENTER;

import java.util.StringJoiner;
import store.utils.StringUtils;

public class Product {
    private static final String PRICE_UNIT = "원";

    private final String name;
    private final Long price;
    private final Stock stock;
    private final Promotion promotion;

    public Product(String name, Long price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = new Stock();
        this.promotion = promotion;
    }

    public long checkProblemQuantity(long requestQuantity) {
        return stock.checkProblemQuantity(promotion, requestQuantity);
    }

    public void addStock(Long quantity, Promotion promotion) {
        if (promotion == null) {
            stock.addNormalQuantity(quantity);
            return;
        }

        stock.addPromotionQuantity(quantity);
    }

    public String getStatus() {
        StringJoiner joiner = new StringJoiner(ENTER);

        if (promotion != null) {
            joiner.add(toPromotionStatus());
        }

        return joiner.add(toNormalStatus())
                .toString();
    }

    public String toNormalStatus() {
        StringJoiner joiner = new StringJoiner(" ");

        return joiner.add("-")
                .add(name)
                .add(toPriceString())
                .add(stock.toNormalQuantityString())
                .toString();
    }

    public String toPromotionStatus() {
        StringJoiner joiner = new StringJoiner(" ");

        return joiner.add("-")
                .add(name)
                .add(toPriceString())
                .add(stock.toPromotionQuantityString())
                .add(promotion.getName())
                .toString();
    }

    private String toPriceString() {
        return StringUtils.numberFormat(price) + PRICE_UNIT;
    }

    public long calculateGiftQuantity(long requestQuantity) {

        return stock.calculateGiftQuantity(promotion, requestQuantity);
    }

    public long sumOriginalPrice(long requestQuantity) {
        long originalQuantity = stock.calculateOriginalPriceQuantity(promotion, requestQuantity);

        return -originalQuantity * price;
    }


    public long calculateSumPrice(long quantity) {
        return quantity * price;
    }


    public void applyStock(long requestQuantity) {
        stock.apply(requestQuantity);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", promotion=" + promotion +
                '}';
    }
}
