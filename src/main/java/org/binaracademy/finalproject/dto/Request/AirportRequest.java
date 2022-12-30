package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequest {
    @NotNull
    private String name;
    @NotNull
    private Long cityId;
}
