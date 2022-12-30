package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.http.MediaType;
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

    @Operation(summary = "Update user details (EndPoint digunakan untuk update info user details)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Update User details",
                            description = "Endpoint dapat digunakan setelah user login untuk dapat merubah info lengkap user ",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 201,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 3,\n" +
                                    "        \"displayName\": \"annisa jkt48\",\n" +
                                    "        \"birthDate\": \"2003-02-23\",\n" +
                                    "        \"gender\": \"woman\",\n" +
                                    "        \"address\": \"Jakarta\",\n" +
                                    "        \"user_id\": 3,\n" +
                                    "        \"user\": {\n" +
                                    "            \"id\": 3,\n" +
                                    "            \"username\": \"annisa\",\n" +
                                    "            \"email\": \"annisa@gmail.com\",\n" +
                                    "            \"password\": \"$2a$10$a/5S6LqdCnpM0o/rf1B94.MoQzAqvT/sZcKN98crLQLIVbdG87dqa\",\n" +
                                    "            \"profile\": \"http://res.cloudinary.com/dcufn29nd/image/upload/v1672382766/wqt9vhosthsvdvgeurns.jpg\",\n" +
                                    "            \"createAt\": \"2022-12-30T13:43:21.01127\",\n" +
                                    "            \"updateAt\": \"2022-12-30T13:46:09.287677\",\n" +
                                    "            \"roles\": [\n" +
                                    "                {\n" +
                                    "                    \"id\": 2,\n" +
                                    "                    \"name\": \"ROLE_USER\"\n" +
                                    "                }\n" +
                                    "            ]\n" +
                                    "        },\n" +
                                    "        \"createAt\": \"2022-12-30T13:43:21.527849\",\n" +
                                    "        \"updateAt\": \"2022-12-30T13:46:09.2836814\"\n" +
                                    "    }\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", content = @Content(examples = {
                    @ExampleObject(name = "Request Error",
                            description = "Tampilan jika request error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 400,\n"
                                    + "    \"message\": \"Request Error Message\",\n"
                                    + "    \"data\": null\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", content = @Content(examples = {
                    @ExampleObject(name = "Server Error",
                            description = "Tampilan jika server error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 500,\n"
                                    + "    \"message\": \"Server Error Message\",\n"
                                    + "    \"data\": null\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
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

    @Operation(summary = "Get user details (EndPoint digunakan untuk mendapatkan info user Details)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Get User details",
                            description = "Endpoint dapat digunakan setelah user login untuk mendapatkan info user Details",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 3,\n" +
                                    "        \"displayName\": \"annisa jkt48\",\n" +
                                    "        \"birthDate\": \"2003-02-23\",\n" +
                                    "        \"gender\": \"woman\",\n" +
                                    "        \"address\": \"Jakarta\",\n" +
                                    "        \"user_id\": 3,\n" +
                                    "        \"user\": {\n" +
                                    "            \"id\": 3,\n" +
                                    "            \"username\": \"annisa\",\n" +
                                    "            \"email\": \"annisa@gmail.com\",\n" +
                                    "            \"password\": \"$2a$10$a/5S6LqdCnpM0o/rf1B94.MoQzAqvT/sZcKN98crLQLIVbdG87dqa\",\n" +
                                    "            \"profile\": \"http://res.cloudinary.com/dcufn29nd/image/upload/v1672382766/wqt9vhosthsvdvgeurns.jpg\",\n" +
                                    "            \"createAt\": \"2022-12-30T13:43:21.01127\",\n" +
                                    "            \"updateAt\": \"2022-12-30T13:46:09.287677\",\n" +
                                    "            \"roles\": [\n" +
                                    "                {\n" +
                                    "                    \"id\": 2,\n" +
                                    "                    \"name\": \"ROLE_USER\"\n" +
                                    "                }\n" +
                                    "            ]\n" +
                                    "        },\n" +
                                    "        \"createAt\": \"2022-12-30T13:43:21.527849\",\n" +
                                    "        \"updateAt\": \"2022-12-30T13:46:09.283681\"\n" +
                                    "    }\n" +
                                    "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", content = @Content(examples = {
                    @ExampleObject(name = "Request Error",
                            description = "Tampilan jika request error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 400,\n"
                                    + "    \"message\": \"Request Error Message\",\n"
                                    + "    \"data\": null\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", content = @Content(examples = {
                    @ExampleObject(name = "Server Error",
                            description = "Tampilan jika server error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 500,\n"
                                    + "    \"message\": \"Server Error Message\",\n"
                                    + "    \"data\": null\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
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
