package store.repository;

import java.time.LocalDate;
import java.util.Optional;
import store.domain.Promotion;

public interface PromotionRepository {

    Optional<Promotion> findById(String key);

    Promotion save(String key, Promotion promotion, LocalDate localDate);

    void clear();
}
