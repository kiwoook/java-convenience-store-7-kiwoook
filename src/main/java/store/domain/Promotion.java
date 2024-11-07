package store.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {

    private static final int ONE_DAY_OFFSET = 1;
    private final String name;
    private final long buyQuantity;
    private final long getQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, long buyQuantity, long getQuantity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion create(String name, long buy, long get, LocalDate startDate, LocalDate endDate) {
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public boolean isValidPromotion(LocalDate currentDate) {
        return currentDate.isAfter(startDate.minusDays(ONE_DAY_OFFSET)) && currentDate.isBefore(
                endDate.plusDays(ONE_DAY_OFFSET));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotion promotion = (Promotion) o;
        return buyQuantity == promotion.buyQuantity && getQuantity == promotion.getQuantity && Objects.equals(name,
                promotion.name)
                && Objects.equals(startDate, promotion.startDate) && Objects.equals(endDate,
                promotion.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buyQuantity, getQuantity, startDate, endDate);
    }

    @Override
    public String toString() {
        return name;
    }
}
