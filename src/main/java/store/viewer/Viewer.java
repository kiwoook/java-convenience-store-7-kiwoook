package store.viewer;

import store.enums.PromptMessage;

public interface Viewer {

    String ERROR_SIGN = "[ERROR] ";

    void promptMessage(PromptMessage promptMessage);

    void printMessage(String message);

    void printError(Exception exception);

    String getInput();
}
