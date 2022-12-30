package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedulePageRequest {

    @NotBlank(message = "departureAiport is required")
    private String departureAiport;
    @NotBlank(message = "arrivalAirport is required")
    private String arrivalAirport;

}
