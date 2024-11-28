package store.view;

public class OutputViewer {

    public static final String ERROR_SIGN = "[ERROR] ";

    public void printError(Exception e) {
        System.out.println(ERROR_SIGN + e.getMessage());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
