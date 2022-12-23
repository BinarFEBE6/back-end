package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.binaracademy.finalproject.dto.Request.UserDetailsRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.helper.utility.ErrorParsingUtility;
import org.binaracademy.finalproject.helper.utility.ImgPatternException;
import org.binaracademy.finalproject.helper.utility.UserNotFoundException;
import org.binaracademy.finalproject.repositories.UserRepo;
import org.binaracademy.finalproject.repositories.UsersDetailsRepo;
import org.binaracademy.finalproject.security.jwt.JwtDecode;
import org.binaracademy.finalproject.services.UploadImageService;
import org.binaracademy.finalproject.services.UserService;
import org.binaracademy.finalproject.services.UsersDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "UserDetails", description = "Operation about User Details")
public class UserDetailsController {
    private final UsersDetailsService userDetailsService;
    private final UsersDetailsRepo usersDetailsRepo;
    private final UploadImageService uploadImageService;
    private final JwtDecode jwtDecode;

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/user/edit_profile/update")
    public ResponseEntity<ResponseData<Object>> update(@ModelAttribute UserDetailsRequest data,Errors errors){
        ResponseData<Object> res = new ResponseData<>();
        try{
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage(ErrorParsingUtility.parse(errors).toString());
                res.setData(null);
                errors.getAllErrors().forEach(x -> log.info(x.toString()));
                return ResponseEntity.badRequest().body(res);
            }
//            Optional<UserDetailsEntity> sample = usersDetailsRepo.findUserDetailsByUserId(jwtDecode.decode().getUserId());
//            if (sample.isEmpty()){
//                res.setSuccess(false);
//                res.setStatusCode(HttpStatus.NOT_FOUND.value());
//                res.setMessage("User not found!");
//                res.setData(null);
//            }
//
//            String profile = uploadImageService.uploadImg(data.getPicture());
//
//            UserDetailsEntity userDetails = sample.get();
//            userDetails.setDisplayName(data.getDisplayName());
//            userDetails.setBirthDate(LocalDate.parse(data.getBirthDate()));
//            userDetails.setAddress(data.getAddress());
//            userDetails.setGender(data.getGender());
//            userDetails.getUser().setProfile(profile);
//            userDetails.getUser().setUpdateAt(LocalDateTime.now());
//            userDetails.setUpdateAt(LocalDateTime.now());
//            UserDetailsEntity userDetails2 = userDetailsService.update(userDetails);
            UserDetailsEntity userDetails = userDetailsService.update2(data, jwtDecode.decode().getUserId());

            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(userDetails);
            return ResponseEntity.ok(res);
        }catch (UserNotFoundException | ImgPatternException | SizeLimitExceededException bad){
            res.setMessage(bad.getMessage());
            res.setStatusCode(HttpStatus.BAD_REQUEST.value());
            res.setSuccess(false);
            res.setData(null);
            return ResponseEntity.badRequest().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/user/edit_profile")
    public ResponseEntity<ResponseData<UserDetailsEntity>> findByGuestId(){
        ResponseData<UserDetailsEntity> response = new ResponseData<>();

        UserDetailsEntity exist = userDetailsService.findByUserid(jwtDecode.decode().getUserId());
        if (exist != null){
            response.setData(exist);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }
        else{
            response.setData(null);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("No user details found");
            return ResponseEntity.internalServerError().body(response);

        }
    }

}
