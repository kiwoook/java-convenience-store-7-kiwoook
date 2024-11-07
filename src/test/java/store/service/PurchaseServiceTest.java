package store.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.PurchaseInfo;
import store.domain.PurchaseInfos;
import store.repository.SingleRepository;
import store.repository.impl.ProductRepository;
import store.repository.impl.PurchaseInfoRepository;
import store.service.impl.PurchaseServiceImpl;

class PurchaseServiceTest {

    @Test
    @DisplayName("해당 제품이 없으면 에러를 반환한다.")
    void validateQuantityTest1(){
        // given

//        List.of(new PurchaseInfo("[제품-10]"))
//        ProductRepository productRepository = new ProductRepository();
//        SingleRepository<PurchaseInfos> purchaseInfosSingleRepository = new PurchaseInfoRepository();
//        PurchaseServiceImpl purchaseService = new PurchaseServiceImpl(purchaseInfosSingleRepository, productRepository);
    }
}
