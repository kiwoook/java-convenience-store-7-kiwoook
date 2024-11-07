package store.repository.impl;

import java.util.List;
import java.util.Optional;
import store.domain.PurchaseInfos;
import store.repository.MapRepository;
import store.repository.SingleRepository;

public class PurchaseInfoRepository implements MapRepository {

    private PurchaseInfos purchaseInfos;


    @Override
    public Optional findById(String key) {
        return Optional.empty();
    }

    @Override
    public Object save(String key, Object object) {
        return null;
    }

    @Override
    public List getAll() {
        return List.of();
    }
}
