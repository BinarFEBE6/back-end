package org.binaracademy.finalproject.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequest implements Serializable {
    @NotNull(message = "display name shouldn't be null")
    private String displayName;
//    @NotNull(message = "birth date shouldn't be null")
    private String birthDate;
    @NotNull(message = "gender shouldn't be null")
    private String gender;
    @NotNull(message = "address shouldn't be null")
    private String address;
    private MultipartFile picture;
}
