package org.binaracademy.finalproject.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketData {
    private String guest;
    private String departure;
    private String arrival;
    private String seat;
    private LocalDate date;
    private LocalTime boarding;
    private String category;
}
