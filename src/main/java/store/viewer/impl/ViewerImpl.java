package store.viewer.impl;

import camp.nextstep.edu.missionutils.Console;
import store.enums.PromptMessage;
import store.viewer.Viewer;

public class ViewerImpl implements Viewer {
    @Override
    public void promptMessage(PromptMessage promptMessage) {
        System.out.println(promptMessage.getMessage());
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(Exception exception) {
        System.out.println(ERROR_SIGN + exception.getMessage());
    }

    @Override
    public String getInput() {
        return Console.readLine();
    }
}
