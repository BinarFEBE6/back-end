package org.binaracademy.finalproject.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class CountryResponse {

    private Long id;
    private String countryName;
    private List<CityResponse> city;

}
