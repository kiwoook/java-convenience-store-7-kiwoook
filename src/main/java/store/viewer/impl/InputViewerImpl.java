package store.viewer.impl;

import camp.nextstep.edu.missionutils.Console;
import store.enums.PromptMessage;
import store.viewer.InputViewer;

public class InputViewerImpl implements InputViewer {
    @Override
    public void promptMessage(PromptMessage promptMessage) {
        System.out.println(promptMessage.getMessage());
    }

    @Override
    public String getInput() {
        return Console.readLine();
    }

    @Override
    public void promptFreeItem(String productName, Long quantity) {
        String message = String.format("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", productName, quantity);
        System.out.println(message);
    }

    @Override
    public void promptNonDiscount(String productName, Long quantity) {
        String message = String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", productName, quantity);
        System.out.println(message);
    }
}
