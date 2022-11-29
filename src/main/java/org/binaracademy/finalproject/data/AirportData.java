package org.binaracademy.finalproject.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportData {
    private String name;
    private List<PesawatData> pesawat;
}
