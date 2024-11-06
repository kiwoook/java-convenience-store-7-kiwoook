package store.service;

public interface StoreService {

    // 파일을 읽어와 프로모션을 저장
    void savePromotion();

    // 파일을 읽어와 제품을 저장
    void saveProduct();
}
