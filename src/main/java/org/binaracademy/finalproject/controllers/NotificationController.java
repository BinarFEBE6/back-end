package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Response.NotificationResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.NotificationEntity;
import org.binaracademy.finalproject.security.jwt.JwtDecode;
import org.binaracademy.finalproject.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "Operation about Notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtDecode jwtDecode;

    @Operation(summary = "Get all notif user (EndPoint digunakan untuk mendapatkan semua notification user)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Get Notif user",
                            description = "Endpoint ini dapat digunakan ketika user ingin mengetahui notif apa aja yang dimiliki, jiak berhasil" +
                                    "makan akan dibalikan data seperti data diatas dari notif user yang ada",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"content\": \"Anda berhasil melakukan order penerbangan Jakarta - Bali\",\n" +
                                    "            \"orderId\": 1,\n" +
                                    "            \"userId\": 1,\n" +
                                    "            \"date\": \"2022-12-07T12:56:40.444513\"\n" +
                                    "        }\n" +
                                    "    ]\n" +
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
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/notification")
    public ResponseEntity<ResponseData<List<NotificationResponse>>> getNotification(){
        ResponseData<List<NotificationResponse>> response = new ResponseData<>();
        try {
            List<NotificationResponse> notifications = new ArrayList<>();
            notificationService.getAllNotifByUserId(jwtDecode.decode().getUserId()).forEach(
                    s -> notifications.add(NotificationResponse.builder()
                    .id(s.getId())
                    .content(s.getContent())
                    .orderId(s.getOrderId())
                    .userId(s.getUserId())
                    .date(s.getCreateAt())
                    .build()));
            response.setData(notifications);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Get notif (EndPoint digunakan untuk mendapatkan detail notification)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Get Notif By Id",
                            description = "Endpoint ini dapat digunakan untuk mendapatkan detail notification yang ada seperti data diatas yang diberikan," +
                                    "mungkin dapat digunakan untuk dapat melikat order detail jika fiture nya ingin dibuat",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"content\": \"Anda berhasil melakukan order penerbangan Jakarta - Bali\",\n" +
                                    "        \"status\": true,\n" +
                                    "        \"orderId\": 1,\n" +
                                    "        \"userId\": 1,\n" +
                                    "        \"createAt\": \"2022-12-07T12:56:40.444513\",\n" +
                                    "        \"updateAt\": null\n" +
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
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getNotification/{notifId}")
    public ResponseEntity<ResponseData<NotificationEntity>> getOneNotification(@PathVariable("notifId") Long notifId){
        ResponseData<NotificationEntity> response = new ResponseData<>();
        try {
            NotificationEntity notifications = notificationService.getById(notifId);
            response.setData(notifications);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
