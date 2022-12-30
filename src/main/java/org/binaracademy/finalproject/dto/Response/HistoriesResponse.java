package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.binaracademy.finalproject.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class HistoriesResponse {
    private BigDecimal totalPrice;
    private BigDecimal tax;
    private BigDecimal totalPay;
    private ScheduleEntity schedule;
    private List<TicketEntity> ticket;
    private LocalDateTime expiredAt;
}
