package store.viewer.impl;

import static store.utils.Constants.ENTER;

import store.dto.Message;
import store.viewer.OutputViewer;

public class OutputViewerImpl implements OutputViewer {
    @Override
    public void printError(Exception exception) {
        System.out.println(ERROR_SIGN + exception.getMessage());

    }

    @Override
    public void printResult(Message message) {
        System.out.println(message);

    }

    @Override
    public void printExplain() {
        System.out.println(ENTER + "안녕하세요. W편의점입니다." + ENTER + "현재 보유하고 있는 상품입니다." + ENTER);
    }
}
