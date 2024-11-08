package store.repository.impl;

import java.util.Optional;
import store.domain.OrderInfos;
import store.repository.SingleRepository;

public class PurchaseInfosRepository implements SingleRepository<OrderInfos> {

    private OrderInfos purchaseInfos;

    @Override
    public OrderInfos save(OrderInfos object) {
        this.purchaseInfos = object;
        return purchaseInfos;
    }

    @Override
    public Optional<OrderInfos> get() {
        return Optional.ofNullable(this.purchaseInfos);
    }
}
