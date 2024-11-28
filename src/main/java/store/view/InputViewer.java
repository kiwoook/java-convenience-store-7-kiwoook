package store.view;

import static store.utils.Constants.ENTER;

import camp.nextstep.edu.missionutils.Console;
import store.model.Confirmation;

public class InputViewer {

    public String requestProduct(String productStatus) {
        System.out.println("안녕하세요. W편의점입니다.\n" + "현재 보유하고 있는 상품입니다.");
        System.out.println();
        System.out.println(productStatus);
        System.out.println();

        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();

    }

    public Confirmation retryPurchase() {
        System.out.println(ENTER + "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Confirmation.of(Console.readLine());
    }

    public Confirmation checkGiftQuantity(String productName) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", productName);
        System.out.println();

        return Confirmation.of(Console.readLine());
    }

    public Confirmation checkOriginalPriceQuantity(String productName, Long problemQuantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", productName, -problemQuantity);
        System.out.println();

        return Confirmation.of(Console.readLine());
    }

    public Confirmation confirmMembership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)\n");

        return Confirmation.of(Console.readLine());
    }
}
