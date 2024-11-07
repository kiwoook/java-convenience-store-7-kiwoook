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
}
