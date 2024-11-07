package store.viewer;

public interface OutputViewer {
    String ERROR_SIGN = "[ERROR] ";

    void printError(Exception exception);

    void printResult(String message);
}
