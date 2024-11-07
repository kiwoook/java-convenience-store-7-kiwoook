package store.repository.impl;

import java.util.ArrayList;
import java.util.List;
import store.domain.PurchaseVerification;
import store.repository.ListRepository;

public class PurchaseVerificationRepository implements ListRepository<PurchaseVerification> {

    List<PurchaseVerification> database = new ArrayList<>();

    @Override
    public PurchaseVerification save(PurchaseVerification object) {
        database.add(object);
        return object;
    }

    @Override
    public List<PurchaseVerification> getAll() {
        return this.database;
    }
}
