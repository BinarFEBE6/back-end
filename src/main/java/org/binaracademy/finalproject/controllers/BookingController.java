package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.binaracademy.finalproject.dto.Request.BookingOrderRequest;
import org.binaracademy.finalproject.dto.Request.GuestRequest;
import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.dto.Response.BookingResponse;
import org.binaracademy.finalproject.dto.Response.NotificationResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.*;
import org.binaracademy.finalproject.helper.utility.ErrorParsingUtility;
import org.binaracademy.finalproject.security.jwt.JwtDecode;
import org.binaracademy.finalproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/booking")
@Tag(name = "Booking", description = "Operation about Booking")
public class BookingController {
    @Autowired
    GuestService guestService;
    @Autowired
    ContactGuestService contactGuestService;

    @Autowired
    TicketService ticketService;
    @Autowired
    OrderService orderService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    JwtDecode jwtDecode;

    @Operation(summary = "Add guest (EndPoint untuk user melakukan order, digunakan untuk membuat penumpang)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Create guest",
                            description = "EndPoint ini digunakan user ketika setelah memilih schedule untuk membuat penumpang," +
                                    "setelah berhasil melakukan hit endpoint ini maka akan dibalikan data seperti diatas," +
                                    "tolong simpan data gusetId untuk keperluan hit endpoint order ticket nanti",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 201,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"firstName\": \"Adinda\",\n" +
                                    "        \"lastName\": \"Anzani\",\n" +
                                    "        \"birthDate\": \"1998-04-06\",\n" +
                                    "        \"nationality\": \"Indonesia\",\n" +
                                    "        \"country\": \"Indonesia\",\n" +
                                    "        \"passport\": \"A8989888\",\n" +
                                    "        \"endPassport\": \"2025-12-06\",\n" +
                                    "        \"googleId\": \"1i1iu1i1iu\",\n" +
                                    "        \"userId\": 1,\n" +
                                    "        \"contactId\": 1,\n" +
                                    "        \"user\": null,\n" +
                                    "        \"contact\": null,\n" +
                                    "        \"createAt\": \"2022-12-06T10:51:44.1881502\",\n" +
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
    @PostMapping("/guest")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody GuestRequest data, Errors errors){
        ResponseData<Object> res = new ResponseData<>();
        try{
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage(ErrorParsingUtility.parse(errors).toString());
                res.setData(null);
                return ResponseEntity.badRequest().body(res);
            }
            ContactGuestEntity contact = contactGuestService.create(new ContactGuestEntity(null, data.getGuestFirstname(),
                    data.getGuestLastname(), data.getNoTelp(), data.getEmail(), null, null));
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(guestService.create(new GuestEntity(null, data.getFirstName(), data.getLastName(), data.getBirthDate(),
                    data.getNationality(), data.getCountry(), data.getPassport(), data.getEndPassport(), data.getGoogleId(),
                    jwtDecode.decode().getUserId(), contact.getId(), null, null, null, null)));
            return ResponseEntity.ok(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @Operation(summary = "Add Order (EndPoint untuk user melakukan order, digunakan untuk membuat order ticket)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Create Order and ticket",
                            description = "EndPoint ini digunakan user untuk order ticket, ini digunakan setelah hit endpoint add guest," +
                                    "jika berhasil makan akan dikembalikan data seperti diatas dan juga notification data yang dapat digunakan" +
                                    "untuk in app notification",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"order\": {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"status\": true,\n" +
                                    "            \"totalPrice\": 750000.00,\n" +
                                    "            \"tax\": 75000.000,\n" +
                                    "            \"totalPay\": 825000.000,\n" +
                                    "            \"expiredAt\": \"2022-12-07T12:13:26.9916063\",\n" +
                                    "            \"userEmail\": \"budi@gmail.com\",\n" +
                                    "            \"scheduleId\": 1,\n" +
                                    "            \"schedule\": null,\n" +
                                    "            \"createAt\": \"2022-12-07T00:13:26.9916063\",\n" +
                                    "            \"updateAt\": null,\n" +
                                    "            \"ticket\": [\n" +
                                    "                {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"status\": true,\n" +
                                    "                    \"scheduleId\": 1,\n" +
                                    "                    \"seatId\": 1,\n" +
                                    "                    \"guestId\": 1,\n" +
                                    "                    \"orderId\": 1,\n" +
                                    "                    \"schedule\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"departureAiport\": \"Jakarta\",\n" +
                                    "                        \"arrivalAirport\": \"Bali\",\n" +
                                    "                        \"price\": 750000.00,\n" +
                                    "                        \"maxSeat\": 1000,\n" +
                                    "                        \"date\": \"2022-12-12\",\n" +
                                    "                        \"scheduleTimeId\": 1,\n" +
                                    "                        \"categoryClassId\": 1,\n" +
                                    "                        \"pesawatId\": 1,\n" +
                                    "                        \"scheduleTime\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"day\": \"sunday\",\n" +
                                    "                            \"depatureTime\": \"10:00:00\",\n" +
                                    "                            \"arrivalTime\": \"12:00:00\",\n" +
                                    "                            \"createAt\": \"2022-12-07T00:11:51.009746\",\n" +
                                    "                            \"updateAt\": null\n" +
                                    "                        },\n" +
                                    "                        \"categoryClass\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"name\": \"Bussiness\",\n" +
                                    "                            \"createAt\": \"2022-12-07T00:11:51.259727\",\n" +
                                    "                            \"updateAt\": null\n" +
                                    "                        },\n" +
                                    "                        \"pesawat\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"name\": \"Airbus A330-200\",\n" +
                                    "                            \"airportId\": 1,\n" +
                                    "                            \"airport\": {\n" +
                                    "                                \"id\": 1,\n" +
                                    "                                \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                                \"cityId\": 1,\n" +
                                    "                                \"createAt\": \"2022-12-07T00:11:49.707284\",\n" +
                                    "                                \"updateAt\": null,\n" +
                                    "                                \"city\": {\n" +
                                    "                                    \"id\": 1,\n" +
                                    "                                    \"name\": \"Jakarta\",\n" +
                                    "                                    \"createAt\": \"2022-12-07T00:11:49.66041\",\n" +
                                    "                                    \"updateAt\": null,\n" +
                                    "                                    \"countryId\": 1,\n" +
                                    "                                    \"country\": {\n" +
                                    "                                        \"id\": 1,\n" +
                                    "                                        \"name\": \"Indonesia\",\n" +
                                    "                                        \"createAt\": \"2022/12/07 00:11:48\",\n" +
                                    "                                        \"updateAt\": null\n" +
                                    "                                    }\n" +
                                    "                                }\n" +
                                    "                            },\n" +
                                    "                            \"createAt\": \"2022-12-07T00:11:49.72291\",\n" +
                                    "                            \"updateAt\": null\n" +
                                    "                        },\n" +
                                    "                        \"createAt\": \"2022-12-07T00:13:17.285517\",\n" +
                                    "                        \"updateAt\": null\n" +
                                    "                    },\n" +
                                    "                    \"seat\": null,\n" +
                                    "                    \"guest\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"firstName\": \"Adinda\",\n" +
                                    "                        \"lastName\": \"Anzani\",\n" +
                                    "                        \"birthDate\": \"1998-04-06\",\n" +
                                    "                        \"nationality\": \"Indonesia\",\n" +
                                    "                        \"country\": \"Indonesia\",\n" +
                                    "                        \"passport\": \"A8989888\",\n" +
                                    "                        \"endPassport\": \"2025-12-06\",\n" +
                                    "                        \"googleId\": \"1i1iu1i1iu\",\n" +
                                    "                        \"userId\": 1,\n" +
                                    "                        \"contactId\": 1,\n" +
                                    "                        \"user\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"username\": \"budi123\",\n" +
                                    "                            \"email\": \"budi@gmail.com\",\n" +
                                    "                            \"password\": \"1234\",\n" +
                                    "                            \"profile\": \"ytt\",\n" +
                                    "                            \"createAt\": \"2022-12-06T00:00:00\",\n" +
                                    "                            \"updateAt\": null,\n" +
                                    "                            \"roles\": []\n" +
                                    "                        },\n" +
                                    "                        \"contact\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"firstName\": \"Adinda\",\n" +
                                    "                            \"lastName\": \"Anzani\",\n" +
                                    "                            \"noTelp\": \"08162716176\",\n" +
                                    "                            \"email\": \"adindaanz@gamil.com\",\n" +
                                    "                            \"createAt\": \"2022-12-07T00:13:03.024304\",\n" +
                                    "                            \"updateAt\": null\n" +
                                    "                        },\n" +
                                    "                        \"createAt\": \"2022-12-07T00:13:03.045285\",\n" +
                                    "                        \"updateAt\": null\n" +
                                    "                    },\n" +
                                    "                    \"order\": null,\n" +
                                    "                    \"createAt\": \"2022-12-07T00:13:27.1370439\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                }\n" +
                                    "            ]\n" +
                                    "        },\n" +
                                    "        \"notification\": {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"content\": \"Anda berhasil melakukan order penerbangan Jakarta - Bali\",\n" +
                                    "            \"orderId\": 1,\n" +
                                    "            \"userId\": 1,\n" +
                                    "            \"date\": \"2022-12-07T00:13:27.515114\"\n" +
                                    "        }\n" +
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
    @PostMapping("/add")
    public ResponseEntity<ResponseData<BookingResponse>> create(@Valid @RequestBody BookingOrderRequest bookingOrderRequest, Errors errors){
        ResponseData<BookingResponse> response = new ResponseData<>();
        OrderTicketRequest orderTicketRequest = OrderTicketRequest.builder()
                                                .guestId(bookingOrderRequest.getGuestId())
                                                .scheduleId(bookingOrderRequest.getScheduleId())
                                                .seatId(bookingOrderRequest.getSeatId())
                                                .userEmail(jwtDecode.decode().getEmail())
                                                .build();
        try {
            if(errors.hasErrors()){
                response.setData(null);
                response.setSuccess(false);
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setMessage(ErrorParsingUtility.parse(errors).toString());
                return ResponseEntity.badRequest().body(response);
            }
            OrderEntity order = orderService.create(orderTicketRequest);
            List<TicketEntity> ticketEntities = ticketService.create(orderTicketRequest, order.getId());
            ticketEntities.forEach(ticket -> {
                ticket.setSchedule(scheduleService.getOneSchedule(ticket.getScheduleId()));
                ticket.setGuest(guestService.getById(ticket.getGuestId()));
            });
            order.setTicket(ticketEntities);
            NotificationEntity notification = notificationService.create(order.getId());

            response.setData(BookingResponse.builder()
                    .order(order)
                    .notification(NotificationResponse.builder()
                            .id(notification.getId())
                            .content(notification.getContent())
                            .orderId(notification.getOrderId())
                            .userId(notification.getUserId())
                            .date(notification.getCreateAt())
                            .build())
                    .build());
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
