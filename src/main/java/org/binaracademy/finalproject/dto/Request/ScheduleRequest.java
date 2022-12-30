package org.binaracademy.finalproject.dto.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    @NotEmpty(message = "departureAiport is required")
    private String departureAiport;
    @NotEmpty(message = "arrivalAirport is required")
    private String arrivalAirport;
    @NotNull(message = "price is required")
    private BigDecimal price;
    @NotNull(message = "maxSeat is required")
    private Integer maxSeat;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "date is required")
    private LocalDate date;
    @NotNull(message = "scheduleTimeId is required")
    private Long scheduleTimeId;
    @NotNull(message = "categoryClassId is required")
    private Long categoryClassId;
    @NotNull(message = "pesawatId is required")
    private Long pesawatId;

}
