package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String email;
    private LocalDateTime orderDate;
    private List<TicketResponse> orderDetail = new ArrayList<>();
    private BigDecimal totalPrice;
    private BigDecimal tax;
    private BigDecimal totalPay;
}
