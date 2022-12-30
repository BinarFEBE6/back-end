package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String content;
    private Long orderId;
    private Long userId;
    private LocalDateTime date;

}
