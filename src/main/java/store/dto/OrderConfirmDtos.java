package store.dto;

import java.util.List;

public record OrderConfirmDtos(List<OrderConfirmDto> items) {

    public static OrderConfirmDtos create(List<OrderConfirmDto> checkPurchaseInfoDtos) {
        return new OrderConfirmDtos(checkPurchaseInfoDtos);
    }
}
