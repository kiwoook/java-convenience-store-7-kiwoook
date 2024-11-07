package store.domain;

import static store.enums.ErrorMessage.EXCEED_PURCHASE;
import static store.enums.ErrorMessage.INVALID_FILE_FORMAT;
import static store.utils.Constants.ENTER;
import static store.utils.Constants.SPACE_BAR;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.StringJoiner;
import store.exception.InvalidFormatException;

public class Product {

    private static final String CURRENCY_UNIT = "원";
    private static final String DIVIDER = "-";

    private final String name;
    private final long price;
    private final Stock stock;
    private Promotion promotion;

    public Product(String name, long price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = new Stock();
        updatePromotion(promotion);
    }

    public Product(String name, long price, Stock stock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public void validStock(Long requestCount) {
        if (stock.total() < requestCount) {
            throw new IllegalArgumentException(EXCEED_PURCHASE.getMessage());
        }
    }

    public void updatePromotion(Promotion promotion) {
        if (this.promotion != null && !this.promotion.equals(promotion)) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }

        this.promotion = promotion;
    }

    public void validPrice(long dtoPrice) {
        if (this.price != dtoPrice) {
            throw new InvalidFormatException(INVALID_FILE_FORMAT.getMessage());
        }
    }

    public void addNormalQuantity(long quantity) {
        this.stock.addNormalProduct(quantity);
    }

    public void addPromotionQuantity(long quantity) {
        this.stock.addPromotionProduct(quantity);
    }

    public Long calculateRequiredQuantity(Long requestQuantity) {
        return stock.remainQuantity(requestQuantity, promotion);
    }


    @Override
    public String toString() {
        StringJoiner enterJoiner = new StringJoiner(ENTER);

        if (promotion != null) {
            enterJoiner.add(promotionStatus());
        }

        return enterJoiner.add(normalStatus())
                .toString();
    }

    private String promotionStatus() {
        StringJoiner spaceBarJoiner = new StringJoiner(SPACE_BAR);
        return spaceBarJoiner.add(DIVIDER)
                .add(this.name)
                .add(priceString())
                .add(stock.toPromotionCountString())
                .add(promotion.toString())
                .toString();
    }

    private String normalStatus() {
        StringJoiner spaceBarJoiner = new StringJoiner(SPACE_BAR);
        return spaceBarJoiner.add(DIVIDER)
                .add(this.name)
                .add(priceString())
                .add(stock.toNormalCountString())
                .toString();
    }

    private String priceString() {
        DecimalFormat format = new DecimalFormat("#,###");

        return format.format(price) + CURRENCY_UNIT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name) && Objects.equals(stock,
                product.stock) && Objects.equals(promotion, product.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, stock, promotion);
    }
}
