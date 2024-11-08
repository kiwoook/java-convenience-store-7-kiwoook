package store.repository;

import java.util.Optional;
import store.domain.Promotion;

public interface PromotionRepository {

    Optional<Promotion> findById(String key);

    Promotion save(String key, Promotion object);

    void clear();
}
