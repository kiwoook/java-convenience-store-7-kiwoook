package store.viewer;

import store.domain.vo.ProductName;
import store.enums.PromptMessage;

public interface InputViewer {

    void promptMessage(PromptMessage promptMessage);

    String getInput();

    void promptFreeItem(ProductName productName, long freeQuantity);

    void promptNonDiscount(ProductName productName, long additionalQuantity);
}
