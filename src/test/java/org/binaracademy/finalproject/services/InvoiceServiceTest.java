package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.data.TicketData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class InvoiceServiceTest {
    @Autowired
    InvoiceService invoiceService;

    @Test
    @DisplayName("Create pdf for invoice")
    void generateFile(){
        TicketData data = TicketData.builder()
                .date(LocalDate.parse("2022-12-30"))
                .guest("Jamal Cassanova")
                .boarding(LocalTime.parse("08:30:00"))
                .category("Business")
                .seat("014")
                .departure("Jakarta")
                .arrival("Hanoi").build();
        Assertions.assertNotNull(invoiceService.generateInvoice(null, data, "Ticket"));
    }
}
