package store.model;

import java.util.ArrayList;
import java.util.List;

public record PurchaseInfos(List<PurchaseInfo> items) {

    private static final String SEPARATOR = ",";


    // TODO stream으로 바꾸는 법 해보기
    public static PurchaseInfos from(String input) {
        List<PurchaseInfo> purchaseInfoList = new ArrayList<>();
        String[] split = input.split(SEPARATOR);

        for (String value : split) {
            purchaseInfoList.add(PurchaseInfo.from(value));
        }

        return new PurchaseInfos(purchaseInfoList);
    }

    @Override
    public String toString() {
        return "PurchaseInfos{" +
                "items=" + items +
                '}';
    }
}
