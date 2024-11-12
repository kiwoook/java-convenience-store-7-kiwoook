package store.viewer.impl;

import static store.utils.Constants.ENTER;

import camp.nextstep.edu.missionutils.Console;
import store.domain.vo.ProductName;
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
    public void promptFreeItem(ProductName productName, long quantity) {
        String message = String.format("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", productName);
        System.out.print(ENTER + message + ENTER);
    }

    @Override
    public void promptNonDiscount(ProductName productName, long quantity) {
        String message = String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", productName, quantity);
        System.out.print(ENTER + message + ENTER);
    }
}
