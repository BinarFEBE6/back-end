package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.Request.UserRegisterRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.security.jwt.JwtDecode;
import org.binaracademy.finalproject.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operation about User")
public class UserController {

    private final UserService userService;
    private final JwtDecode jwtDecode;

    @Operation(summary = "(ini test, Tidak digunakan)")
    @GetMapping("/test")
    public String test(){
        return "OK";
    }

    @Operation(summary = "Add User (ini test, Tidak digunakan)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Create User",
                            description = "Menampilkan balikan dari User regist",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 201,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 3,\n" +
                                    "        \"username\": \"steven123\",\n" +
                                    "        \"email\": \"steven@gmail.com\",\n" +
                                    "        \"password\": \"123456\",\n" +
                                    "        \"profile\": null,\n" +
                                    "        \"createAt\": \"2022-12-07T13:40:56.8821821\",\n" +
                                    "        \"updateAt\": \"2022-12-07T13:40:56.8821821\",\n" +
                                    "        \"roles\": []\n" +
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
                                    + "    \"data\": []\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", content = @Content(examples = {
                    @ExampleObject(name = "Server Error",
                            description = "Tampilan jika server error",
                            value = "{\n"
                                    + "    \"success\": false,\n"
                                    + "    \"statusCode\": 500,\n"
                                    + "    \"message\": \"Server Error Message\",\n"
                                    + "    \"data\": []\n"
                                    + "}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody UserRegisterRequest data, Errors errors){
        try{
            ResponseData<Object> res = new ResponseData();
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed! ");
                res.setData(null);
                errors.getAllErrors().forEach(x ->{
                    System.out.println(x);
                });
                return ResponseEntity.badRequest().body(res);
            }

            UserEntity user = userService.create(new UserEntity(data.getUsername(),
                    data.getEmail(), data.getPassword()));

            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(user);

            return ResponseEntity.ok(res);
        }catch (Exception e){
            ResponseData res = new ResponseData();
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @Operation(summary = "(ini test, Tidak digunakan)")
    @GetMapping("/test/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        log.info(jwtDecode.decode().getUserId().toString());
        log.info(jwtDecode.decode().getUsername());
        log.info(jwtDecode.decode().getEmail());
        log.info(jwtDecode.decode().getToken());
        log.info(jwtDecode.decode().getType());
        return "User Content.";
    }


}
