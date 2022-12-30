package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Response.CityResponse;
import org.binaracademy.finalproject.dto.Response.CountryResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.services.CityService;
import org.binaracademy.finalproject.services.CountryService;
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
@Tag(name = "Country", description = "Operation about country")
public class CountryController {

    private final CountryService countryService;

    private final CityService cityService;

    @Operation(summary = "Get all country (EndPoint digunakan untuk mendapatkan semua country)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "List Country",
                            description = "Endpoint digunakan untuk mendapatkan data country seperti yang diberikan diatas," +
                                    "kemudian data tersebut dapat digunakan untuk mengirimkan countryId ke City untuk mendapatkan city",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"countryName\": \"Indonesia\",\n" +
                                    "            \"city\": [\n" +
                                    "                {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"cityName\": \"Jakarta\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"id\": 2,\n" +
                                    "                    \"cityName\": \"Bali (Denpasar)\"\n" +
                                    "                }\n" +
                                    "            ]\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 2,\n" +
                                    "            \"countryName\": \"Australia\",\n" +
                                    "            \"city\": [\n" +
                                    "                {\n" +
                                    "                    \"id\": 3,\n" +
                                    "                    \"cityName\": \"Sydney\"\n" +
                                    "                },\n" +
                                    "                {\n" +
                                    "                    \"id\": 4,\n" +
                                    "                    \"cityName\": \"Melbourne\"\n" +
                                    "                }\n" +
                                    "            ]\n" +
                                    "        }\n"
                                    + "    ]\n"
                                    + "}")
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
    @GetMapping("/getCountry")
    public ResponseEntity<ResponseData<List<CountryResponse>>> getCountry(){

        ResponseData<List<CountryResponse>> response = new ResponseData<>();

        try {
            List<CountryResponse> countryResponses = new ArrayList<>();
            countryService.getAll().forEach(country ->{
                List<CityResponse> cityResponses = new ArrayList<>();
                cityService.getCity(country.getId()).forEach(city
                        -> cityResponses.add(CityResponse.builder()
                        .id(city.getId())
                        .cityName(city.getName()).build()));
                countryResponses.add(CountryResponse.builder()
                        .id(country.getId())
                        .countryName(country.getName())
                        .city(cityResponses).build());
            });

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Successfully!");
            response.setData(countryResponses);
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
