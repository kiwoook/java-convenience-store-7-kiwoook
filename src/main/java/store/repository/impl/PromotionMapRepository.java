package store.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import store.domain.Promotion;
import store.repository.MapRepository;

public class PromotionMapRepository implements MapRepository<Promotion> {

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
    public List<Promotion> getAll() {
        return database.values()
                .stream()
                .toList();
    }

    @Override
    public void clear() {
        this.database.clear();
    }
}
