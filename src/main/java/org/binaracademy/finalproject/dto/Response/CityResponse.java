package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CityResponse {

    private Long id;
    private String cityName;

}
