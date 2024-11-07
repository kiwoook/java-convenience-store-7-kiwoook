package store.viewer.impl;

import store.viewer.OutputViewer;

public class OutputViewerImpl implements OutputViewer {
    @Override
    public void printError(Exception exception) {
        System.out.println(ERROR_SIGN + exception.getMessage());

    }

    @Override
    public void printResult(String message) {
        System.out.println(message);

    }
}
