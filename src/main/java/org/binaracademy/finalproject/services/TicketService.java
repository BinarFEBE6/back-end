package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.TicketEntity;

import java.util.List;

public interface TicketService {

    List<TicketEntity> create(OrderTicketRequest orderTicketRequest, Long id);

    List<TicketEntity> update(OrderTicketRequest orderTicketRequest);
    TicketEntity findByGuestId(Long guestId);
    TicketEntity getById(Long ticketId);

}
