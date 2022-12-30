package org.binaracademy.finalproject.dto.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequest {
    @NotNull(message = "first name shouldn't be null")
    private String firstName;
    @NotNull(message = "last name shouldn't be null")
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String nationality;
    private String country;
    private String passport;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endPassport;
    private String googleId;
//    @NotNull(message = "user id shouldn't be null")
//    private Long userId;
    private String guestFirstname;
    private String guestLastname;
    @Pattern(regexp = "^[0-9]*$", message = "phone number is invalid")
    private String noTelp;
    @Email(message = "email address is invalid")
    private String email;
}
