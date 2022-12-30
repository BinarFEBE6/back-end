package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.binaracademy.finalproject.dto.Response.HistoriesResponse;
import org.binaracademy.finalproject.dto.Response.OrderResponse;
import org.binaracademy.finalproject.dto.Response.TicketResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.OrderEntity;
import org.binaracademy.finalproject.security.jwt.JwtDecode;
import org.binaracademy.finalproject.services.InvoiceService;
import org.binaracademy.finalproject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Operation about Order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    JwtDecode jwtDecode;
    @Autowired
    HttpServletResponse response;

    @Operation(summary = "Get order histories (Endpoint digunakan untuk mendapatkan semua history user)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(examples = {
                    @ExampleObject(name = "List Orders",
                            description = "Menampilkan semua transaksi yang telah dipesan",
                            value = "{\n"
                                    + "    \"success\": true,\n"
                                    + "    \"statusCode\": 200,\n"
                                    + "    \"message\": \"Successfully!\",\n"
                                    + "    \"data\": [\n"
                                    + "        {\n"
                                    + "            \"totalPrice\": 1600000,\n"
                                    + "            \"tax\": \"160000\"\n"
                                    + "            \"totalPay\": \"176000\"\n"
                                    + "            \"scheduleId\": {\n"
                                        + "                 \"id\": 1,\n"
                                    + "                     \"departureAiport\": \"Jakarta\",\n"
                                    + "                     \"arrivalAirport\": \"Bali\",\n"
                                    + "                     \"price\": 750000,\n"
                                    + "                     \"maxSeat\": 1000,\n"
                                    + "                     \"date\": \"2022-12-10\",\n"
                                    + "                     \"scheduleTimeId\": 1,\n"
                                    + "                     \"categoryClassId\": 1,\n"
                                    + "                     \"pesawatId\": 1,\n"
                                    + "                     \"scheduleTime\": {\n"
                                    + "                             \"id\": 1,\n"
                                    + "                             \"day\": \"sunday\"\n"
                                    + "                             \"departureTime\": \"10:00:00\"\n"
                                    + "                             \"arrivalTime\": \"12:00:00\"\n"
                                    + "                         },\n"
                                    + "                     \"categoryClass\": {\n"
                                    + "                             \"id\": 1,\n"
                                    + "                             \"name\": \"economy\"\n"
                                    + "                            },\n"
                                    + "                     \"pesawat\": {\n"
                                    + "                             \"id\": 1,\n"
                                    + "                             \"name\": \"Airbus A330-200\"\n"
                                    + "                             \"airportId\": 1,\n"
                                    + "                             \"airport\": {\n"
                                    + "                             \"id\": 1,\n"
                                    + "                             \"name\": \"Soekarno-Hatta International Airport\",\n"
                                    + "                             \"cityId\": 1,\n"
                                    + "                             \"createAt\": \"2022-12-16T23:51:15.799493\",\n"
                                    + "                             \"updateAt\": null,\n"
                                    + "                             \"city\": {\n"
                                    + "                                 \"id\": 1,\n"
                                    + "                                 \"name\": \"Jakarta\",\n"
                                    + "                                 \"createAt\": \"2022-12-16T23:51:15.533383\",\n"
                                    + "                                 \"updateAt\": null,\n"
                                    + "                                 \"countryId\": 1,\n"
                                    + "                                 \"country\": {\n"
                                    + "                                         \"id\": 1,\n"
                                    + "                                         \"name\": \"Indonesia\",\n"
                                    + "                                         \"createAt\": \"2022/12/16 23:51:07\",\n"
                                    + "                                         \"updateAt\": null\n"
                                    + "                                     }\n"
                                    + "                                 }\n"
                                    + "                             },\n"
                                    + "                             \"createAt\": \"2022-12-16T23:51:15.838933\",\n"
                                    + "                             \"updateAt\": null,\n"
                                    + "                            },\n"
                                    + "                     \"createAt\": \"2022-12-06T09:43:35.0729626\",\n"
                                    + "                     \"updateAt\": null\n"
                                    + "                 },\n"
                                    + "            \"expiredAt\": \"2022-12-08\"\n"
                                    + "        }\n"
                                    + "    ]\n"
                                    + "}")
            },  mediaType = MediaType.APPLICATION_JSON_VALUE)),
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
    @GetMapping("/getHistories")
    public ResponseEntity<ResponseData<List<HistoriesResponse>>> getHistories() {

        ResponseData<List<HistoriesResponse>> response = new ResponseData<>();

        try {
            List<HistoriesResponse> data = new ArrayList<>();
            orderService.FindMyOrders(jwtDecode.decode().getEmail()).forEach(order ->
                    data.add(HistoriesResponse.builder()
                            .totalPrice(order.getTotalPrice())
                            .tax(order.getTax())
                            .totalPay(order.getTotalPay())
                            .schedule(order.getSchedule())
                            .ticket(order.getTicket())
                            .expiredAt(order.getExpiredAt()).build()));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Get order Invoice (Endpoint digunakan untuk mendownload invoice berdasarkan ID order)")
    @GetMapping(value = "/generateOrder/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateFile(@PathVariable Long id){
        try{
            OrderEntity order = orderService.getById(id);
            List<TicketResponse> ticket = new ArrayList<>();
            order.getTicket().forEach(x -> ticket.add(TicketResponse.builder()
                    .guest(x.getGuest().getFirstName()+ " "+x.getGuest().getLastName())
                    .departure(x.getSchedule().getDepartureAiport())
                    .arrival(x.getSchedule().getArrivalAirport())
                    .pesawat(x.getSchedule().getPesawat().getName())
                    .seat(x.getSeat().getSeatName())
                    .date(x.getSchedule().getDate())
                    .link("https://binar-academy-terbangin.herokuapp.com/api/generateTicket/"+ x.getId()).build()));
            OrderResponse sample = OrderResponse.builder()
                    .email(order.getUserEmail())
                    .orderDate(order.getCreateAt())
                    .orderDetail(ticket)
                    .totalPrice(order.getTotalPrice())
                    .tax(order.getTax())
                    .totalPay(order.getTotalPay())
                    .build();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; fileName=\"invoice"+ LocalDateTime.now().toString()+".pdf\"");
            ByteArrayInputStream invoice = new ByteArrayInputStream(invoiceService.generateInvoice(sample, null, "Order"));
            IOUtils.copy(invoice, response.getOutputStream());
            log.info("Success generate invoice with orderId : {}", id);
            response.flushBuffer();
        }catch (Exception e){
            log.warn("Error generate invoice with message : {}", e.getMessage());
        }
    }
}
