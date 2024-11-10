package store.mock;

import store.dto.Message;
import store.viewer.OutputViewer;

public class MockOutputViewer implements OutputViewer {
    @Override
    public void printError(Exception exception) {

    }

    @Override
    public void printResult(Message message) {

    }

    @Override
    public void printExplain() {

    }
}
