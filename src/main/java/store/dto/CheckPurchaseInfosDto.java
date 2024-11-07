package store.dto;

import java.util.List;

public class CheckPurchaseInfosDto {
    List<CheckPurchaseInfosDto> checkPurchaseInfoDtos;

    public CheckPurchaseInfosDto(List<CheckPurchaseInfosDto> checkPurchaseInfoDtos) {
        this.checkPurchaseInfoDtos = checkPurchaseInfoDtos;
    }

    public List<CheckPurchaseInfosDto> getCheckPurchaseInfoDtos() {
        return checkPurchaseInfoDtos;
    }
}
