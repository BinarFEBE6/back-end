package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Response.ScheduleTimeResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.services.ScheduleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Schedule Time", description = "Operation about schedule time")
public class ScheduleTimeController {
    @Autowired
    ScheduleTimeService scheduleTimeService;

    @Operation(summary = "Get schedule times (Endpoint digunakan untuk mendapatkan schedule time)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(examples = {
                    @ExampleObject(name = "List schedule times",
                            description = "Menampilkan semua data jadwal waktu",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"day\": \"sunday\",\n" +
                                    "            \"departureTime\": \"10:00:00\",\n" +
                                    "            \"arrivalTime\": \"12:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 2,\n" +
                                    "            \"day\": \"monday\",\n" +
                                    "            \"departureTime\": \"09:00:00\",\n" +
                                    "            \"arrivalTime\": \"17:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 3,\n" +
                                    "            \"day\": \"tuesday\",\n" +
                                    "            \"departureTime\": \"09:00:00\",\n" +
                                    "            \"arrivalTime\": \"13:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 4,\n" +
                                    "            \"day\": \"wednesday\",\n" +
                                    "            \"departureTime\": \"12:00:00\",\n" +
                                    "            \"arrivalTime\": \"16:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 5,\n" +
                                    "            \"day\": \"thursday\",\n" +
                                    "            \"departureTime\": \"09:00:00\",\n" +
                                    "            \"arrivalTime\": \"12:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 6,\n" +
                                    "            \"day\": \"thursday\",\n" +
                                    "            \"departureTime\": \"13:00:00\",\n" +
                                    "            \"arrivalTime\": \"17:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 7,\n" +
                                    "            \"day\": \"friday\",\n" +
                                    "            \"departureTime\": \"09:00:00\",\n" +
                                    "            \"arrivalTime\": \"17:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 8,\n" +
                                    "            \"day\": \"saturday\",\n" +
                                    "            \"departureTime\": \"14:00:00\",\n" +
                                    "            \"arrivalTime\": \"17:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 9,\n" +
                                    "            \"day\": \"saturday\",\n" +
                                    "            \"departureTime\": \"14:30:00\",\n" +
                                    "            \"arrivalTime\": \"17:00:00\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 10,\n" +
                                    "            \"day\": \"saturday\",\n" +
                                    "            \"departureTime\": \"15:00:00\",\n" +
                                    "            \"arrivalTime\": \"17:00:00\"\n" +
                                    "        }\n" +
                                    "    ]\n" +
                                    "}")
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
    @GetMapping("/getScheduleTime")
    public ResponseEntity<ResponseData<List<ScheduleTimeResponse>>> getTimes() {

        ResponseData<List<ScheduleTimeResponse>> response = new ResponseData<>();

        try {
            List<ScheduleTimeResponse> data = new ArrayList<>();
            scheduleTimeService.getAll().forEach(time ->
                    data.add(ScheduleTimeResponse.builder()
                            .id(time.getId())
                            .day(time.getDay())
                            .arrivalTime(time.getArrivalTime())
                            .departureTime(time.getDepatureTime()).build()));
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
}
