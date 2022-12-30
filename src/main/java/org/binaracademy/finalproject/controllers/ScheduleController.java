package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Request.SchedulePageRequest;
import org.binaracademy.finalproject.dto.Request.ScheduleRequest;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.binaracademy.finalproject.helper.utility.ErrorParsingUtility;
import org.binaracademy.finalproject.helper.utility.StatusCode;
import org.binaracademy.finalproject.services.ScheduleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Schedule", description = "Operation about Schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private static final String SUCCSES = "Successfully!";

    @Operation(summary = "Add Schedule (EndPoint digunakan untuk membuat schedule(admin only))")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Create Schedule",
                            description = "EndPoint ini dapat digunakan untuk membuat schedule(admin only)," +
                                    "untuk dapat menggunkan ini bisa hit endpoint seperti categoryClassId, scheduleTimeId," +
                                    " dan pesawatId terlebih dahulu agar schedule dapat dibuat, jika berhasil maka akan mengembalikan data" +
                                    "seperti di atas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"departureAiport\": \"Jakarta\",\n" +
                                    "        \"arrivalAirport\": \"Bali\",\n" +
                                    "        \"price\": 750000,\n" +
                                    "        \"maxSeat\": 1000,\n" +
                                    "        \"date\": \"2022-12-10\",\n" +
                                    "        \"scheduleTimeId\": 1,\n" +
                                    "        \"categoryClassId\": 1,\n" +
                                    "        \"pesawatId\": 1,\n" +
                                    "        \"scheduleTime\": null,\n" +
                                    "        \"categoryClass\": null,\n" +
                                    "        \"pesawat\": null,\n" +
                                    "        \"createAt\": \"2022-12-06T09:43:35.0729626\",\n" +
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/schedule")
    public ResponseEntity<ResponseData<ScheduleEntity>> create(@Valid @RequestBody ScheduleRequest scheduleRequest, Errors errors){

        ResponseData<ScheduleEntity> response = new ResponseData<>();

        if (errors.hasErrors()) {
            response.setStatusCode(StatusCode.BAD_REQUEST);
            response.setSuccess(false);
            response.setMessage(ErrorParsingUtility.parse(errors).toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            ScheduleEntity schedule = new ScheduleEntity(null, scheduleRequest.getDepartureAiport(), scheduleRequest.getArrivalAirport(),
                    scheduleRequest.getPrice(), scheduleRequest.getMaxSeat(), scheduleRequest.getDate(), scheduleRequest.getScheduleTimeId(),
                    scheduleRequest.getCategoryClassId(), scheduleRequest.getPesawatId(), null, null, null, LocalDateTime.now(), null);

            response.setData(scheduleService.create(schedule));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(SUCCSES);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Paging And Sort Schedule (EndPoint digunakan untuk mendapatkan schedule berdasarkan tujuan dan sudah paging)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Get Paging Schedule",
                            description = "Endpoint ini dapat digunakan sebelum user melakukan booking order ticket, untuk mendapatkan schedule berbentuk paging " +
                                    "berdasarkan dari mana dan kemana tujuan user mencari penerbangan, contoh paging dapat ditulis seperti ini api/getSchedule/5/0/asc," +
                                    "kemudian scheduleId dari sini yang dipilih user akan digunakan untuk melakukan booking order ticket, jika berhasil akan" +
                                    "mengembalikan data seperti di atas, tolong simpan scheduleId yang dipilih user untuk order ticket",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"content\": [\n" +
                                    "            {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"departureAiport\": \"Jakarta\",\n" +
                                    "                \"arrivalAirport\": \"Bali\",\n" +
                                    "                \"price\": 750000,\n" +
                                    "                \"maxSeat\": 1000,\n" +
                                    "                \"date\": \"2022-12-20\",\n" +
                                    "                \"scheduleTimeId\": 1,\n" +
                                    "                \"categoryClassId\": 1,\n" +
                                    "                \"pesawatId\": 1,\n" +
                                    "                \"scheduleTime\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"day\": \"sunday\",\n" +
                                    "                    \"depatureTime\": \"10:00:00\",\n" +
                                    "                    \"arrivalTime\": \"12:00:00\",\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:26.051811\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                },\n" +
                                    "                \"categoryClass\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Bussiness\",\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:26.129933\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                },\n" +
                                    "                \"pesawat\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Airbus A330-200\",\n" +
                                    "                    \"airportId\": 1,\n" +
                                    "                    \"airport\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                        \"cityId\": 1,\n" +
                                    "                        \"createAt\": \"2022-12-06T09:36:25.674855\",\n" +
                                    "                        \"updateAt\": null,\n" +
                                    "                        \"city\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"name\": \"Jakarta\",\n" +
                                    "                            \"createAt\": \"2022-12-06T09:36:25.65923\",\n" +
                                    "                            \"updateAt\": null,\n" +
                                    "                            \"countryId\": 1,\n" +
                                    "                            \"country\": {\n" +
                                    "                                \"id\": 1,\n" +
                                    "                                \"name\": \"Indonesia\",\n" +
                                    "                                \"createAt\": \"2022/12/06 09:36:25\",\n" +
                                    "                                \"updateAt\": null\n" +
                                    "                            }\n" +
                                    "                        }\n" +
                                    "                    },\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:25.690479\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                },\n" +
                                    "                \"createAt\": \"2022-12-06T09:43:19.783778\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            },\n" +
                                    "            {\n" +
                                    "                \"id\": 2,\n" +
                                    "                \"departureAiport\": \"Jakarta\",\n" +
                                    "                \"arrivalAirport\": \"Bali\",\n" +
                                    "                \"price\": 750000,\n" +
                                    "                \"maxSeat\": 1000,\n" +
                                    "                \"date\": \"2022-12-10\",\n" +
                                    "                \"scheduleTimeId\": 1,\n" +
                                    "                \"categoryClassId\": 1,\n" +
                                    "                \"pesawatId\": 1,\n" +
                                    "                \"scheduleTime\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"day\": \"sunday\",\n" +
                                    "                    \"depatureTime\": \"10:00:00\",\n" +
                                    "                    \"arrivalTime\": \"12:00:00\",\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:26.051811\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                },\n" +
                                    "                \"categoryClass\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Bussiness\",\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:26.129933\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                },\n" +
                                    "                \"pesawat\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Airbus A330-200\",\n" +
                                    "                    \"airportId\": 1,\n" +
                                    "                    \"airport\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                        \"cityId\": 1,\n" +
                                    "                        \"createAt\": \"2022-12-06T09:36:25.674855\",\n" +
                                    "                        \"updateAt\": null,\n" +
                                    "                        \"city\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"name\": \"Jakarta\",\n" +
                                    "                            \"createAt\": \"2022-12-06T09:36:25.65923\",\n" +
                                    "                            \"updateAt\": null,\n" +
                                    "                            \"countryId\": 1,\n" +
                                    "                            \"country\": {\n" +
                                    "                                \"id\": 1,\n" +
                                    "                                \"name\": \"Indonesia\",\n" +
                                    "                                \"createAt\": \"2022/12/06 09:36:25\",\n" +
                                    "                                \"updateAt\": null\n" +
                                    "                            }\n" +
                                    "                        }\n" +
                                    "                    },\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:25.690479\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                },\n" +
                                    "                \"createAt\": \"2022-12-06T09:43:35.072963\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            }\n" +
                                    "        ],\n" +
                                    "        \"pageable\": {\n" +
                                    "            \"sort\": {\n" +
                                    "                \"empty\": false,\n" +
                                    "                \"unsorted\": false,\n" +
                                    "                \"sorted\": true\n" +
                                    "            },\n" +
                                    "            \"offset\": 0,\n" +
                                    "            \"pageSize\": 5,\n" +
                                    "            \"pageNumber\": 0,\n" +
                                    "            \"paged\": true,\n" +
                                    "            \"unpaged\": false\n" +
                                    "        },\n" +
                                    "        \"totalElements\": 2,\n" +
                                    "        \"totalPages\": 1,\n" +
                                    "        \"last\": true,\n" +
                                    "        \"size\": 5,\n" +
                                    "        \"number\": 0,\n" +
                                    "        \"sort\": {\n" +
                                    "            \"empty\": false,\n" +
                                    "            \"unsorted\": false,\n" +
                                    "            \"sorted\": true\n" +
                                    "        },\n" +
                                    "        \"numberOfElements\": 2,\n" +
                                    "        \"first\": true,\n" +
                                    "        \"empty\": false\n" +
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
    @PostMapping("/getSchedule/{size}/{page}/{sort}")
    public ResponseEntity<ResponseData<Iterable<ScheduleEntity>>> getPageSchedule(@RequestBody SchedulePageRequest schedulePageRequest,
                                                                             @PathVariable("size") int size, @PathVariable("page") int page,
                                                                              @PathVariable("sort") String sort){

        ResponseData<Iterable<ScheduleEntity>> response = new ResponseData<>();

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("schedule_id"));
            if (sort.equalsIgnoreCase("desc")){
                pageable = PageRequest.of(page, size, Sort.by("schedule_id").descending());
            }
            response.setData(scheduleService.getPageFromTo(schedulePageRequest.getDepartureAiport(),
                    schedulePageRequest.getArrivalAirport(), pageable));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(SUCCSES);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "All Schedule (EndPoint digunakan untuk mendapatkan schedule berdasarkan tujuan tanpa paging)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Get All Schedule",
                            description = "EndPoint ini dapat digunakan ketika user ingin melakukan penerbangan dari mana ke tujuan apa" +
                                    "kemudian scheduleId dari sini yang dipilih user akan digunakan untuk melakukan booking order ticket, jika berhasil akan " +
                                    "mengembalikan data seperti di atas, tolong simpan scheduleId yang dipilih user untuk order ticket",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"departureAiport\": \"Jakarta\",\n" +
                                    "            \"arrivalAirport\": \"Bali\",\n" +
                                    "            \"price\": 750000,\n" +
                                    "            \"maxSeat\": 1000,\n" +
                                    "            \"date\": \"2022-12-20\",\n" +
                                    "            \"scheduleTimeId\": 1,\n" +
                                    "            \"categoryClassId\": 1,\n" +
                                    "            \"pesawatId\": 1,\n" +
                                    "            \"scheduleTime\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"day\": \"sunday\",\n" +
                                    "                \"depatureTime\": \"10:00:00\",\n" +
                                    "                \"arrivalTime\": \"12:00:00\",\n" +
                                    "                \"createAt\": \"2022-12-06T09:36:26.051811\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            },\n" +
                                    "            \"categoryClass\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Bussiness\",\n" +
                                    "                \"createAt\": \"2022-12-06T09:36:26.129933\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            },\n" +
                                    "            \"pesawat\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Airbus A330-200\",\n" +
                                    "                \"airportId\": 1,\n" +
                                    "                \"airport\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                    \"cityId\": 1,\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:25.674855\",\n" +
                                    "                    \"updateAt\": null,\n" +
                                    "                    \"city\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Jakarta\",\n" +
                                    "                        \"createAt\": \"2022-12-06T09:36:25.65923\",\n" +
                                    "                        \"updateAt\": null,\n" +
                                    "                        \"countryId\": 1,\n" +
                                    "                        \"country\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"name\": \"Indonesia\",\n" +
                                    "                            \"createAt\": \"2022/12/06 09:36:25\",\n" +
                                    "                            \"updateAt\": null\n" +
                                    "                        }\n" +
                                    "                    }\n" +
                                    "                },\n" +
                                    "                \"createAt\": \"2022-12-06T09:36:25.690479\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            },\n" +
                                    "            \"createAt\": \"2022-12-06T09:43:19.783778\",\n" +
                                    "            \"updateAt\": null\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 2,\n" +
                                    "            \"departureAiport\": \"Jakarta\",\n" +
                                    "            \"arrivalAirport\": \"Bali\",\n" +
                                    "            \"price\": 750000,\n" +
                                    "            \"maxSeat\": 1000,\n" +
                                    "            \"date\": \"2022-12-10\",\n" +
                                    "            \"scheduleTimeId\": 1,\n" +
                                    "            \"categoryClassId\": 1,\n" +
                                    "            \"pesawatId\": 1,\n" +
                                    "            \"scheduleTime\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"day\": \"sunday\",\n" +
                                    "                \"depatureTime\": \"10:00:00\",\n" +
                                    "                \"arrivalTime\": \"12:00:00\",\n" +
                                    "                \"createAt\": \"2022-12-06T09:36:26.051811\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            },\n" +
                                    "            \"categoryClass\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Bussiness\",\n" +
                                    "                \"createAt\": \"2022-12-06T09:36:26.129933\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            },\n" +
                                    "            \"pesawat\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Airbus A330-200\",\n" +
                                    "                \"airportId\": 1,\n" +
                                    "                \"airport\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                    \"cityId\": 1,\n" +
                                    "                    \"createAt\": \"2022-12-06T09:36:25.674855\",\n" +
                                    "                    \"updateAt\": null,\n" +
                                    "                    \"city\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Jakarta\",\n" +
                                    "                        \"createAt\": \"2022-12-06T09:36:25.65923\",\n" +
                                    "                        \"updateAt\": null,\n" +
                                    "                        \"countryId\": 1,\n" +
                                    "                        \"country\": {\n" +
                                    "                            \"id\": 1,\n" +
                                    "                            \"name\": \"Indonesia\",\n" +
                                    "                            \"createAt\": \"2022/12/06 09:36:25\",\n" +
                                    "                            \"updateAt\": null\n" +
                                    "                        }\n" +
                                    "                    }\n" +
                                    "                },\n" +
                                    "                \"createAt\": \"2022-12-06T09:36:25.690479\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            },\n" +
                                    "            \"createAt\": \"2022-12-06T09:43:35.072963\",\n" +
                                    "            \"updateAt\": null\n" +
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
    @PostMapping("/getSchedule")
    public ResponseEntity<ResponseData<List<ScheduleEntity>>> getSchedule(@RequestBody SchedulePageRequest schedulePageRequest){

        ResponseData<List<ScheduleEntity>> response = new ResponseData<>();

        try {
            response.setData(scheduleService.getFromTo(schedulePageRequest.getDepartureAiport(), schedulePageRequest.getArrivalAirport()));
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage(SUCCSES);
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
