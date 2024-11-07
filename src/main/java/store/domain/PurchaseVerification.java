package store.domain;

import java.util.Objects;
import store.dto.PurchaseConfirmDto;
import store.enums.Confirmation;

public class PurchaseVerification {

    private static final long ZERO = 0;

    private final String name;
    private final long quantity;

    public PurchaseVerification(String name, long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public static PurchaseVerification from(PurchaseConfirmDto confirmDto) {
        return new PurchaseVerification(confirmDto.name(), confirmDto.requestQuantity());
    }

    public static PurchaseVerification of(PurchaseConfirmDto confirmDto, Confirmation confirmation) {
        return new PurchaseVerification(confirmDto.name(), processQuantity(confirmDto, confirmation));
    }

    public static long processQuantity(PurchaseConfirmDto confirmDto, Confirmation confirmation) {
        if (confirmDto.problemQuantity() > 0 && confirmation.equals(Confirmation.YES)) {
            return confirmDto.requestQuantity() + confirmDto.problemQuantity();
        }
        if (confirmDto.problemQuantity() < 0 && confirmation.equals(Confirmation.NO)) {
            return ZERO;
        }

        return confirmDto.requestQuantity();
    }

    public PurchaseConfirmDto toDto() {
        return PurchaseConfirmDto.create(name, quantity, ZERO);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseVerification that = (PurchaseVerification) o;
        return quantity == that.quantity && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }
}
