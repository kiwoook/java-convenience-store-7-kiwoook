package store.dto;

import java.util.List;

public class PromotionDtos {

    List<PromotionDto> promotionDtoList;

    public PromotionDtos(List<PromotionDto> promotionDtoList) {
        this.promotionDtoList = promotionDtoList;
    }

    public List<PromotionDto> getPromotionDtoList() {
        return promotionDtoList;
    }


}
