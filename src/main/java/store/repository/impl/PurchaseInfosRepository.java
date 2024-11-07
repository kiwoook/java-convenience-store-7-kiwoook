package store.repository.impl;

import java.util.Optional;
import store.domain.PurchaseInfos;
import store.repository.SingleRepository;

public class PurchaseInfosRepository implements SingleRepository<PurchaseInfos> {

    private PurchaseInfos purchaseInfos;

    @Override
    public PurchaseInfos save(PurchaseInfos object) {
        this.purchaseInfos = object;
        return purchaseInfos;
    }

    @Override
    public Optional<PurchaseInfos> get() {
        return Optional.ofNullable(this.purchaseInfos);
    }
}
