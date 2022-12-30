package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.services.PesawatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Pesawat", description = "Operation about Pesawat")
public class PesawatController {
    @Autowired
    PesawatService pesawatService;

    @Operation(summary = "Get all pesawat (Endpoint digunakan untuk mendapatkan semua pesawat)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "List Pesawat",
                            description = "Endpoint dapat digunakan untuk mendapatkan list pesawat yang ada",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"name\": \"Airbus A330-200\",\n" +
                                    "            \"airportId\": 1,\n" +
                                    "            \"airport\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                \"cityId\": 1,\n" +
                                    "                \"createAt\": \"2022-12-23T18:05:28.96649\",\n" +
                                    "                \"updateAt\": null,\n" +
                                    "                \"city\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Jakarta\",\n" +
                                    "                    \"createAt\": \"2022-12-23T18:05:28.930223\",\n" +
                                    "                    \"updateAt\": null,\n" +
                                    "                    \"countryId\": 1,\n" +
                                    "                    \"country\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Indonesia\",\n" +
                                    "                        \"createAt\": \"2022/12/23 18:05:28\",\n" +
                                    "                        \"updateAt\": null\n" +
                                    "                    }\n" +
                                    "                }\n" +
                                    "            },\n" +
                                    "            \"createAt\": \"2022-12-23T18:05:29.015825\",\n" +
                                    "            \"updateAt\": null\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 2,\n" +
                                    "            \"name\": \"Airbus A330-300\",\n" +
                                    "            \"airportId\": 1,\n" +
                                    "            \"airport\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                \"cityId\": 1,\n" +
                                    "                \"createAt\": \"2022-12-23T18:05:28.96649\",\n" +
                                    "                \"updateAt\": null,\n" +
                                    "                \"city\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Jakarta\",\n" +
                                    "                    \"createAt\": \"2022-12-23T18:05:28.930223\",\n" +
                                    "                    \"updateAt\": null,\n" +
                                    "                    \"countryId\": 1,\n" +
                                    "                    \"country\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Indonesia\",\n" +
                                    "                        \"createAt\": \"2022/12/23 18:05:28\",\n" +
                                    "                        \"updateAt\": null\n" +
                                    "                    }\n" +
                                    "                }\n" +
                                    "            },\n" +
                                    "            \"createAt\": \"2022-12-23T18:05:29.054868\",\n" +
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getAllPesawat")
    public ResponseEntity<ResponseData<List<PesawatEntity>>> getAll(){
        ResponseData<List<PesawatEntity>> response = new ResponseData<>();
        try{
            List<PesawatEntity> data = pesawatService.getAll();
            response.setSuccess(true);
            response.setMessage("Successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            response.setData(data);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Failed!");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Get pesawat by Airport id (Endpoint digunakan untuk mendapatkan data pesawat berdasarkan ID airport)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Data Pesawat by Airport id",
                            description = "Endpoint dapat digunakan untuk mendapatkan data pesawat yang ada berdasarkan ID airport",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"name\": \"Airbus A330-200\",\n" +
                                    "            \"airportId\": 1,\n" +
                                    "            \"airport\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                \"cityId\": 1,\n" +
                                    "                \"createAt\": \"2022-12-23T18:05:28.96649\",\n" +
                                    "                \"updateAt\": null,\n" +
                                    "                \"city\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Jakarta\",\n" +
                                    "                    \"createAt\": \"2022-12-23T18:05:28.930223\",\n" +
                                    "                    \"updateAt\": null,\n" +
                                    "                    \"countryId\": 1,\n" +
                                    "                    \"country\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Indonesia\",\n" +
                                    "                        \"createAt\": \"2022/12/23 18:05:28\",\n" +
                                    "                        \"updateAt\": null\n" +
                                    "                    }\n" +
                                    "                }\n" +
                                    "            },\n" +
                                    "            \"createAt\": \"2022-12-23T18:05:29.015825\",\n" +
                                    "            \"updateAt\": null\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 2,\n" +
                                    "            \"name\": \"Airbus A330-300\",\n" +
                                    "            \"airportId\": 1,\n" +
                                    "            \"airport\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "                \"cityId\": 1,\n" +
                                    "                \"createAt\": \"2022-12-23T18:05:28.96649\",\n" +
                                    "                \"updateAt\": null,\n" +
                                    "                \"city\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Jakarta\",\n" +
                                    "                    \"createAt\": \"2022-12-23T18:05:28.930223\",\n" +
                                    "                    \"updateAt\": null,\n" +
                                    "                    \"countryId\": 1,\n" +
                                    "                    \"country\": {\n" +
                                    "                        \"id\": 1,\n" +
                                    "                        \"name\": \"Indonesia\",\n" +
                                    "                        \"createAt\": \"2022/12/23 18:05:28\",\n" +
                                    "                        \"updateAt\": null\n" +
                                    "                    }\n" +
                                    "                }\n" +
                                    "            },\n" +
                                    "            \"createAt\": \"2022-12-23T18:05:29.054868\",\n" +
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getPesawatByAirport/{id}")
    public ResponseEntity<ResponseData<List<PesawatEntity>>> getByAirportId(@PathVariable Long id){
        ResponseData<List<PesawatEntity>> response = new ResponseData<>();
        try{
            List<PesawatEntity> data = pesawatService.getByAirportId(id);
            response.setSuccess(true);
            response.setMessage("Successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            response.setData(data);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Failed!");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Get pesawat by Pesawat id (Endpoint digunakan untuk mendapatkan data pesawat berdasarkan ID pesawat)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Data Pesawat by Pesawat id",
                            description = "Endpoint dapat digunakan untuk mendapatkan data pesawat yang ada berdasarkan ID pesawat",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 200,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"name\": \"Airbus A330-200\",\n" +
                                    "        \"airportId\": 1,\n" +
                                    "        \"airport\": {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"name\": \"Soekarno-Hatta International Airport\",\n" +
                                    "            \"cityId\": 1,\n" +
                                    "            \"createAt\": \"2022-12-23T18:05:28.96649\",\n" +
                                    "            \"updateAt\": null,\n" +
                                    "            \"city\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Jakarta\",\n" +
                                    "                \"createAt\": \"2022-12-23T18:05:28.930223\",\n" +
                                    "                \"updateAt\": null,\n" +
                                    "                \"countryId\": 1,\n" +
                                    "                \"country\": {\n" +
                                    "                    \"id\": 1,\n" +
                                    "                    \"name\": \"Indonesia\",\n" +
                                    "                    \"createAt\": \"2022/12/23 18:05:28\",\n" +
                                    "                    \"updateAt\": null\n" +
                                    "                }\n" +
                                    "            }\n" +
                                    "        },\n" +
                                    "        \"createAt\": \"2022-12-23T18:05:29.015825\",\n" +
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getPesawatById/{id}")
    public ResponseEntity<ResponseData<PesawatEntity>> getById(@PathVariable Long id){
        ResponseData<PesawatEntity> response = new ResponseData<>();
        try{
            PesawatEntity data = pesawatService.getById(id);
            response.setSuccess(true);
            response.setMessage("Successfully!");
            response.setStatusCode(HttpStatus.OK.value());
            response.setData(data);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.setSuccess(false);
            response.setMessage("Failed!");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
