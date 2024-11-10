package store.repository.impl;

import java.util.LinkedList;
import java.util.List;
import store.domain.VerifiedOrder;
import store.domain.VerifiedOrders;
import store.repository.OrderVerificationRepository;

public class OrderVerificationRepositoryImpl implements OrderVerificationRepository {

    private final List<VerifiedOrder> database = new LinkedList<>();

    @Override
    public void save(VerifiedOrder object) {
        database.add(object);
    }

    @Override
    public VerifiedOrders getAll() {
        return new VerifiedOrders(List.copyOf(database));
    }

    @Override
    public void clear() {
        database.clear();
    }
}
