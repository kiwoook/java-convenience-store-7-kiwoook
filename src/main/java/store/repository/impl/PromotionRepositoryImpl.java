package store.repository.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import store.domain.Promotion;
import store.repository.PromotionRepository;

public class PromotionRepositoryImpl implements PromotionRepository {

    private final Map<String, Promotion> database = new HashMap<>();

    @Override
    public Optional<Promotion> findById(String key) {
        return Optional.ofNullable(database.get(key));
    }

    @Override
    public Promotion save(String key, Promotion promotion, LocalDate currentDate) {
        if (database.containsKey(key)) {
            return changeAvailableDate(key, promotion, currentDate);
        }
        return database.put(key, promotion);
    }

    @Override
    public void clear() {
        this.database.clear();
    }

    private Promotion changeAvailableDate(String key, Promotion promotion, LocalDate currentDate) {
        if (promotion.isValidPromotion(currentDate)) {
            return database.put(key, promotion);
        }
        return database.get(key);
    }
}
