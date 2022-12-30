package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMeta {

    private Long userId;
    private String username;
    private String email;
    private String token;
    private String type;

}