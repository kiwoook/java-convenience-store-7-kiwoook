package store.repository;

import java.util.List;
import java.util.Optional;

public interface MapRepository<T> {

    Optional<T> findById(String key);

    T save(String key, T object);

    List<T> getAll();
    
    void clear();
}
