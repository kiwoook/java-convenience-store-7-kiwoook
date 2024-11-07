package store.domain;

import static store.enums.ErrorMessage.INVALID_PURCHASE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class PurchaseInfos {

    private static final String SEPARATOR = ",";
    private final List<PurchaseInfo> purchaseInfoList;

    public PurchaseInfos() {
        this.purchaseInfoList = new ArrayList<>();
    }

    public PurchaseInfos(List<PurchaseInfo> purchaseInfoList) {
        this.purchaseInfoList = purchaseInfoList;
    }

    public PurchaseInfos(String input) {
        validInput(input);
        this.purchaseInfoList = mergePurchaseInfos(input);
    }

    public static PurchaseInfos create(String input) {
        return new PurchaseInfos(input);
    }

    private List<PurchaseInfo> mergePurchaseInfos(String input) {
        List<PurchaseInfo> purchaseInfos = parsePurchaseInfos(input);
        return PurchaseInfo.totalPurchaseCount(purchaseInfos)
                .entrySet()
                .stream()
                .map(entry -> new PurchaseInfo(entry.getKey(), entry.getValue()))
                .toList();
    }

    private List<PurchaseInfo> parsePurchaseInfos(String input) {
        return Arrays.stream(input.split(SEPARATOR))
                .map(PurchaseInfo::create)
                .toList();
    }

    private void validInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }

        boolean startsWith = input.startsWith(SEPARATOR);
        boolean endsWith = input.endsWith(SEPARATOR);

        if (startsWith || endsWith) {
            throw new IllegalArgumentException(INVALID_PURCHASE.getMessage());
        }
    }

    public void addPurchaseInfo(PurchaseInfo purchaseInfo) {
        purchaseInfoList.add(purchaseInfo);
    }

    public void forEach(Consumer<PurchaseInfo> purchaseInfo) {
        purchaseInfoList.forEach(purchaseInfo);
    }

    public int size() {
        return purchaseInfoList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseInfos that = (PurchaseInfos) o;
        return Objects.equals(purchaseInfoList, that.purchaseInfoList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(purchaseInfoList);
    }
}
