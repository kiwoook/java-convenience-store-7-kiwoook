package store.service;

import store.dto.Message;
import store.dto.OrderConfirmDto;
import store.dto.OrderConfirmDtos;
import store.enums.Confirmation;

public interface PurchaseService {

    void create(String input);

    OrderConfirmDtos check();

    void processQuantity(OrderConfirmDto orderConfirmDto);

    void processProblemQuantity(OrderConfirmDto orderConfirmDto, Confirmation confirmation);

    Message getReceipt(Confirmation confirmation);
}
