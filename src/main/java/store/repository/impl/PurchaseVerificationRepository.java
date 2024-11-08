package store.repository.impl;

import java.util.ArrayList;
import java.util.List;
import store.domain.OrderVerification;
import store.repository.ListRepository;

public class PurchaseVerificationRepository implements ListRepository<OrderVerification> {

    List<OrderVerification> database = new ArrayList<>();

    @Override
    public OrderVerification save(OrderVerification object) {
        database.add(object);
        return object;
    }

    @Override
    public List<OrderVerification> getAll() {
        return this.database;
    }

    @Override
    public void clear() {
        this.database.clear();
    }
}
