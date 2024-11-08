package store.viewer;

import store.enums.PromptMessage;

public interface InputViewer {

    void promptMessage(PromptMessage promptMessage);

    String getInput();

    void promptFreeItem(String productName, long freeQuantity);

    void promptNonDiscount(String productName, long additionalQuantity);
}
