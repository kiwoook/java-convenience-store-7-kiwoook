package store.repository.impl;

import java.util.LinkedList;
import java.util.List;
import store.domain.OrderVerificationV2;
import store.domain.OrderVerifications;
import store.repository.OrderVerificationRepository;

public class OrderVerificationRepositoryImpl implements OrderVerificationRepository {

    private final List<OrderVerificationV2> database = new LinkedList<>();

    @Override
    public void save(OrderVerificationV2 object) {
        database.add(object);
    }

    @Override
    public OrderVerifications getAll() {
        return new OrderVerifications(List.copyOf(database));
    }

    @Override
    public void clear() {
        database.clear();
    }
}
