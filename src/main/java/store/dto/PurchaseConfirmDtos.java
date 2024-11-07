package store.dto;

import java.util.List;

public class PurchaseConfirmDtos {
    List<PurchaseConfirmDto> checkPurchaseInfoDtos;

    public PurchaseConfirmDtos(List<PurchaseConfirmDto> checkPurchaseInfoDtos) {
        this.checkPurchaseInfoDtos = checkPurchaseInfoDtos;
    }

    public static PurchaseConfirmDtos create(List<PurchaseConfirmDto> checkPurchaseInfoDtos) {
        return new PurchaseConfirmDtos(checkPurchaseInfoDtos);
    }

    public List<PurchaseConfirmDto> getCheckPurchaseInfoDtos() {
        return checkPurchaseInfoDtos;
    }
}
