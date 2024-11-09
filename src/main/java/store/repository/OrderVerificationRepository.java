package store.repository;

import store.domain.OrderVerificationV2;
import store.domain.OrderVerifications;

public interface OrderVerificationRepository {

    void save(OrderVerificationV2 object);

    OrderVerifications getAll();

    void clear();
}
