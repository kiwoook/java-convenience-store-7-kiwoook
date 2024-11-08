package store.repository;

import java.util.List;

public interface ListRepository<T> {

    T save(T object);

    List<T> getAll();

    void clear();
}
