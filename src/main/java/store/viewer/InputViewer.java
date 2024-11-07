package store.viewer;

import store.enums.PromptMessage;

public interface InputViewer {

    void promptMessage(PromptMessage promptMessage);

    String getInput();
}
