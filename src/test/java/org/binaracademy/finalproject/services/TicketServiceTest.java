package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.repositories.TicketRepo;
import org.binaracademy.finalproject.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TicketServiceTest {

    @MockBean
    private TicketRepo ticketRepo;
    @Autowired
    private TicketService ticketService;

    OrderTicketRequest orderTicketRequest;
    TicketEntity ticketEntity1;
    TicketEntity ticketEntity2;

    @BeforeEach
    void setUp() {
        orderTicketRequest = new OrderTicketRequest(
                List.of(1L, 2L), 1L, List.of(2L, 3L), "Budi@gmail.com");
        ticketEntity1 = new TicketEntity(1L, true, orderTicketRequest.getScheduleId(),
                orderTicketRequest.getSeatId().get(0), orderTicketRequest.getGuestId().get(0),
                1L, null, null, null, null, LocalDateTime.now(), null);
        ticketEntity2 = new TicketEntity(2L, true, orderTicketRequest.getScheduleId(),
                orderTicketRequest.getSeatId().get(1), orderTicketRequest.getGuestId().get(1),
                1L, null, null, null, null, LocalDateTime.now(), null);
    }

    @Test
    @DisplayName("Create Ticket")
    void create() {
        when(ticketRepo.save(ticketEntity1)).thenReturn(ticketEntity1);
        when(ticketRepo.save(ticketEntity2)).thenReturn(ticketEntity2);
        ticketService.create(orderTicketRequest, 1L);
        verify(ticketRepo, times(2)).save(any());
    }

    @Test
    @DisplayName("Update Ticket")
    void update() {
        when(ticketRepo.save(ticketEntity1)).thenReturn(ticketEntity1);
        when(ticketRepo.save(ticketEntity2)).thenReturn(ticketEntity2);
        List<TicketEntity> ticketEntities = ticketService.update(orderTicketRequest);
        assertNotNull(ticketEntities);
    }

    @Test
    @DisplayName("Get Ticket By Guest Id")
    void findByGuestId() {
        when(ticketRepo.findByGuestId(1L)).thenReturn(Optional.of(ticketEntity1));
        assertEquals(ticketEntity1, ticketService.findByGuestId(1L));
        verify(ticketRepo).findByGuestId(1L);
    }

    @Test
    @DisplayName("Get Ticket By Ticket Id")
    void getById() {
        when(ticketRepo.findById(1L)).thenReturn(Optional.of(ticketEntity1));
        assertEquals(ticketEntity1, ticketService.getById(1L));
        verify(ticketRepo).findById(1L);
    }
}