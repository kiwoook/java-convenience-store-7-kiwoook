package store.repository.impl;

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
    public Promotion save(String key, Promotion object) {
        return database.put(key, object);
    }

    @Override
    public void clear() {
        this.database.clear();
    }
}
