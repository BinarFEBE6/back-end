package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.TicketEntity;
import org.binaracademy.finalproject.repositories.TicketRepo;
import org.binaracademy.finalproject.services.TicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepo ticketRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public List<TicketEntity> create(OrderTicketRequest orderTicketRequest, Long id) {
        try{
            List<TicketEntity> ticket = new ArrayList<>();
            orderTicketRequest.getSeatId().forEach(seat -> {
                int index = orderTicketRequest.getSeatId().indexOf(seat);
                ticket.add(ticketRepo.save(new TicketEntity(null, true, orderTicketRequest.getScheduleId(),
                        seat, orderTicketRequest.getGuestId().get(index), id, null, null, null, null, LocalDateTime.now(), null)));
            });
            log.info("Ticket has been created : {}", orderTicketRequest.getUserEmail());
            return ticket;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<TicketEntity> update(OrderTicketRequest orderTicketRequest) {
        try{
            List<TicketEntity> ticket = new ArrayList<>();
            IntStream.range(0, orderTicketRequest.getSeatId().size()).forEach(x -> {
                TicketEntity data = findByGuestId(orderTicketRequest.getGuestId().get(x));
                data.setSeatId(orderTicketRequest.getSeatId().get(x));
                data.setUpdateAt(LocalDateTime.now());
                ticket.add(ticketRepo.save(data));
            });
            log.info("Ticket has been created : {}", orderTicketRequest.getUserEmail());
            return ticket;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    public TicketEntity findByGuestId(Long guestId){
        Optional<TicketEntity> data = ticketRepo.findByGuestId(guestId);
        if(data.isEmpty()){
            return null;
        }
        return data.get();
    }

    @Override
    public TicketEntity getById(Long ticketId) {
        try{
            Optional<TicketEntity> ticket = ticketRepo.findById(ticketId);
            if (ticket.isEmpty()) {
                return null;
            }
            log.info("call getById ticket : {}", ticket.get().getId());
            return ticket.get();
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }
}
