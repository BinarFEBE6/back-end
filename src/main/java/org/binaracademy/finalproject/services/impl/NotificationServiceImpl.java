package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.NotificationEntity;
import org.binaracademy.finalproject.entity.OrderEntity;
import org.binaracademy.finalproject.repositories.NotificationRepo;
import org.binaracademy.finalproject.repositories.OrderRepo;
import org.binaracademy.finalproject.repositories.TicketRepo;
import org.binaracademy.finalproject.services.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final OrderRepo orderRepo;
    private final TicketRepo ticketRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public NotificationEntity create(Long orderId){
        try {
            Optional<OrderEntity> orderOp = orderRepo.findById(orderId);
            if (orderOp.isEmpty()) {
                log.warn("Order empty in method create notification");
                throw new IllegalStateException("Order tidak ada");
            }
            List<Long> userId1 = notificationRepo.findUserId(orderId);
            if (userId1.isEmpty()){
                log.warn("UserId kosong dalam relasi ticket ke guest");
                throw new IllegalStateException("Tidak ada ticket dari user");
            }
            OrderEntity order = orderOp.get();
            String departure = order.getTicket().get(0).getSchedule().getDepartureAiport();
            String arrival = order.getTicket().get(0).getSchedule().getArrivalAirport();
            String content = "Anda berhasil melakukan order penerbangan "+departure+" - "+arrival;
            NotificationEntity entity = new NotificationEntity(
                    null, content, true, orderId, userId1.get(0), null, null, LocalDateTime.now(), null);
            Long notifId = notificationRepo.save(entity).getId();
            Optional<NotificationEntity> notification = notificationRepo.findById(notifId);
            log.info("call create notification succses");
            return notification.orElse(null);
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    @Override
    public NotificationEntity getById(Long notifId){
        try {
            Optional<NotificationEntity> notification = notificationRepo.findById(notifId);
            log.info("call getById notification succses");
            return notification.orElse(null);
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    @Override
    public List<NotificationEntity> getAllNotifByUserId(Long userId){
        try {
            List<NotificationEntity> notifications = notificationRepo.findByUserId(userId);
            log.info("call getAllNotifByUserId notification succses");
            return notifications;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

}
