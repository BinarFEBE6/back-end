package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.data.TicketData;
import org.binaracademy.finalproject.dto.Response.OrderResponse;

public interface InvoiceService {
    byte[] generateInvoice(OrderResponse order, TicketData ticket, String file);
}
