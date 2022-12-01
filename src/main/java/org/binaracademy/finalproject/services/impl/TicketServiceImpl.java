package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.repositories.TicketRepo;
import org.binaracademy.finalproject.services.TicketService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepo ticketRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public List<TicketEntity> create(OrderTicketRequest orderTicketRequest) {
        try{
            Long orderId = ticketRepo.findOrderIdByScheduleIdAndUsername(
                    orderTicketRequest.getScheduleId(), orderTicketRequest.getUserEmail());
            List<TicketEntity> ticket = new ArrayList<>();
            orderTicketRequest.getSeatId().forEach(seat -> {
                int index = orderTicketRequest.getSeatId().indexOf(seat);
                ticket.add(ticketRepo.createOrderDetail(true, orderTicketRequest.getScheduleId(),
                        seat, orderTicketRequest.getGuestId().get(index), orderId));
            });
            log.info("Ticket has been created : {}", orderTicketRequest.getUserEmail());
            return ticket;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }
}
