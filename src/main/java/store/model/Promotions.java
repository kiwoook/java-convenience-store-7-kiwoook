package store.model;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Promotions {

    private static final int DATE_OFFSET = 1;

    private final Map<String, Promotion> items;

    public Promotions() {
        this.items = new LinkedHashMap<>();
    }

    public static Promotions create() {
        return new Promotions();
    }

    public void put(String promotionName, Promotion promotion) {
        items.put(promotionName, promotion);
    }

    public Promotion get(String promotionName) {
        return items.get(promotionName);
    }

    public boolean isValidDate(LocalDate now, LocalDate startDate, LocalDate endDate) {
        return now.minusDays(DATE_OFFSET).isAfter(startDate) && now.plusDays(DATE_OFFSET).isBefore(endDate);
    }

    @Override
    public String toString() {
        return "Promotions{" +
                "items=" + items +
                '}';
    }
}
