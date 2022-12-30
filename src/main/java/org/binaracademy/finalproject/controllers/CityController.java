package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Response.CityResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.services.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "City", description = "Operation about city")
public class CityController {

    private final CityService cityService;

    @Operation(summary = "Get all city (EndPoint digunakan untuk mendapatkan semua city yang ada di Country tertentu)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "List City",
                            description = "Endpoint dapat digunakan setelah mendapatkan countryId, untuk mendapat city yang ada didalam country yang " +
                                    "tersedia seperti data diatas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"cityName\": \"Jakarta\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 2,\n" +
                                    "            \"cityName\": \"Bali (Denpasar)\"\n" +
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
    @GetMapping("/getCity/{countryId}")
    public ResponseEntity<ResponseData<List<CityResponse>>> getCity(@PathVariable("countryId") Long countryId){

        ResponseData<List<CityResponse>> response = new ResponseData<>();

        try {
            List<CityResponse> cityResponses = new ArrayList<>();
            cityService.getCity(countryId).forEach(city
                    -> cityResponses.add(CityResponse.builder()
                    .id(city.getId())
                    .cityName(city.getName()).build()));

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            response.setData(cityResponses);
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
