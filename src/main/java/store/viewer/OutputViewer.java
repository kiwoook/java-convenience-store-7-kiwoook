package store.viewer;

import store.dto.Message;

public interface OutputViewer {
    String ERROR_SIGN = "[ERROR] ";

    void printError(Exception exception);

    void printResult(Message message);

    void printExplain();
}
