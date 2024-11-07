package store.viewer;

import store.enums.PromptMessage;

public interface InputViewer {

    void promptMessage(PromptMessage promptMessage);

    String getInput();

    void promptFreeItem(String productName, Long freeQuantity);

    void promptNonDiscount(String productName, Long additionalQuantity);
}
