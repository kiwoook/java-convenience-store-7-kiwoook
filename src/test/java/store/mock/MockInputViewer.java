package store.mock;

import store.domain.vo.ProductName;
import store.enums.PromptMessage;
import store.viewer.InputViewer;

public class MockInputViewer implements InputViewer {
    @Override
    public void promptMessage(PromptMessage promptMessage) {

    }

    @Override
    public String getInput() {
        return "";
    }

    @Override
    public void promptFreeItem(ProductName productName, long freeQuantity) {

    }

    @Override
    public void promptNonDiscount(ProductName productName, long additionalQuantity) {

    }
}
