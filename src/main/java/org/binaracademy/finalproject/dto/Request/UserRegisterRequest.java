package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @Email(message = "email shouldn't be null")
    private String email;

    @NotNull(message = "username shouldn't be null")
    private String username;

    @NotNull(message = "password address is invalid")
    private String password;
}
