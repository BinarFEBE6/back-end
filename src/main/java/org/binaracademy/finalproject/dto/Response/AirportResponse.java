package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportResponse {
    private Long id;
    private String airportName;
    private String cityName;
    private List<PesawatResponse> pesawat;
}
