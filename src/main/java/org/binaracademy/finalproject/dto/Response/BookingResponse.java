package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.binaracademy.finalproject.entity.OrderEntity;

@Builder
@Data
@AllArgsConstructor
public class BookingResponse {

    private OrderEntity order;
    private NotificationResponse notification;

}
