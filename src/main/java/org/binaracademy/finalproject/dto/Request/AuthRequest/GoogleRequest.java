package org.binaracademy.finalproject.dto.Request.AuthRequest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class GoogleRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String sub;

    private String picture;

    private Set<String> role;
}