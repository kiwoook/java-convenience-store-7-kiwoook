package store.repository;

import store.domain.VerifiedOrder;
import store.domain.VerifiedOrders;

public interface OrderVerificationRepository {

    void save(VerifiedOrder object);

    VerifiedOrders getAll();

    void clear();
}
