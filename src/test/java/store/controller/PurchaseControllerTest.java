package store.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.Message;
import store.dto.OrderConfirmDto;
import store.dto.OrderConfirmDtos;
import store.enums.Confirmation;
import store.mock.MockInputViewer;
import store.mock.MockOutputViewer;
import store.service.PurchaseService;

class PurchaseControllerTest {

    private MockOutputViewer mockOutputViewer;
    private MockInputViewer mockInputViewer;

    @BeforeEach
    void setUp() {
        mockInputViewer = new MockInputViewer();
        mockOutputViewer = new MockOutputViewer();
    }


    @Test
    @DisplayName("서비스에서 다른 에러가 발생하면 종료되어야 한다.")
    void test1() {
        // given
        PurchaseController purchaseController = new PurchaseController(new PurchaseService() {
            @Override
            public void createOrderInfos(String input) {
                throw new RuntimeException("에러 발생!");
            }

            @Override
            public OrderConfirmDtos check() {
                throw new RuntimeException("에러 발생!");
            }

            @Override
            public void processQuantity(OrderConfirmDto orderConfirmDto) {

            }

            @Override
            public void processProblemQuantity(OrderConfirmDto orderConfirmDto, Confirmation confirmation) {

            }

            @Override
            public Message getReceipt(Confirmation confirmation) {
                return null;
            }
        }, mockInputViewer, mockOutputViewer);

        // when && then
        assertThrows(RuntimeException.class, purchaseController::buy);
        assertThrows(RuntimeException.class, purchaseController::check);
    }
}
