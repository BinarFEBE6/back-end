package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.binaracademy.finalproject.dto.Request.AirportRequest;
import org.binaracademy.finalproject.dto.Response.AirportResponse;
import org.binaracademy.finalproject.dto.Response.PesawatResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.entity.AirportEntity;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.services.AirportService;
import org.binaracademy.finalproject.services.PesawatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/airport")
@Tag(name = "Airport", description = "Operation about Airport")
public class AirportController {
    @Autowired
    AirportService airportService;

    @Autowired
    PesawatService pesawatService;

    @Operation(summary = "Add Airport (EndPoint digunakan untuk membuat airpot)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Create Airport",
                            description = "Endpoint yang dapat digunakan untuk membuat data airport, untuk dapat membuat airport harus" +
                                    "memiliki data dari cityId, jika berhasil akan menampilkan data seperti diatas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 201,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 23,\n" +
                                    "        \"name\": \"Halim Airport\",\n" +
                                    "        \"cityId\": 1,\n" +
                                    "        \"createAt\": \"2022-12-06T10:26:28.4931181\",\n" +
                                    "        \"updateAt\": null,\n" +
                                    "        \"city\": null\n" +
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
    @PostMapping("/add")
    public ResponseEntity<ResponseData<Object>> create(@Valid @RequestBody AirportRequest data, Errors errors){
        try {
            ResponseData<Object> res = new ResponseData<>();
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed!");
                res.setData(null);
                return ResponseEntity.badRequest().body(res);
            }
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(airportService.create(new AirportEntity(null, data.getName(), data.getCityId(), null, null, null)));
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            ResponseData<Object> res = new ResponseData<>();
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @Operation(summary = "Update Airport (EndPoint digunakan untuk update airpot)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Update Airport",
                            description = "Endpoint dapat digunakan untuk update airport, untuk dapat update airpot butuh airport id," +
                                    "jika berhasil akan menampilkan data sperti diatas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 201,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 23,\n" +
                                    "        \"name\": \"Bandar Udara Halim Perdanakusuma\",\n" +
                                    "        \"cityId\": 1,\n" +
                                    "        \"createAt\": \"2022-12-06T10:29:28.174699\",\n" +
                                    "        \"updateAt\": \"2022-12-06T10:31:52.0424716\",\n" +
                                    "        \"city\": {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"name\": \"Jakarta\",\n" +
                                    "            \"createAt\": \"2022-12-06T10:29:19.12533\",\n" +
                                    "            \"updateAt\": null,\n" +
                                    "            \"countryId\": 1,\n" +
                                    "            \"country\": {\n" +
                                    "                \"id\": 1,\n" +
                                    "                \"name\": \"Indonesia\",\n" +
                                    "                \"createAt\": \"2022/12/06 10:29:19\",\n" +
                                    "                \"updateAt\": null\n" +
                                    "            }\n" +
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
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<Object>> update(@PathVariable("id") Long id, @Valid @RequestBody AirportRequest data, Errors errors){
        ResponseData<Object> res = new ResponseData<>();
        try{
            if(errors.hasErrors()){
                res.setSuccess(false);
                res.setStatusCode(HttpStatus.BAD_REQUEST.value());
                res.setMessage("Failed!");
                res.setData(null);
                return ResponseEntity.badRequest().body(res);
            }
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMessage("Successfully!");
            res.setData(airportService.update(id, new AirportEntity(null, data.getName(), data.getCityId(), null, null, null)));
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @Operation(summary = "Get all Airport (EndPoint digunakan untuk mendapatkan semua airpot)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "List Airport",
                            description = "Endpoint dapat digunakan untuk mendapatkan semua data dari airport yang tersedia," +
                                    "jika berhasil akan menampilkan data seperti diatas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 202,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": [\n" +
                                    "        {\n" +
                                    "            \"id\": 1,\n" +
                                    "            \"airportName\": \"Soekarno-Hatta International Airport\",\n" +
                                    "            \"cityName\": \"Jakarta\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 2,\n" +
                                    "            \"airportName\": \"Ngurah Rai International Airport\",\n" +
                                    "            \"cityName\": \"Bali (Denpasar)\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 3,\n" +
                                    "            \"airportName\": \"Sydney Airport\",\n" +
                                    "            \"cityName\": \"Sydney\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"id\": 4,\n" +
                                    "            \"airportName\": \"Melbourne Airport\",\n" +
                                    "            \"cityName\": \"Melbourne\"\n" +
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
    @GetMapping("/getAirports")
    public ResponseEntity<ResponseData<List<AirportResponse>>> getAll(){
        ResponseData<List<AirportResponse>> res = new ResponseData<>();
        try{
            List<AirportResponse> data = new ArrayList<>();
            airportService.getAll().forEach(airport ->{
                List<PesawatResponse> pesawatResponses = new ArrayList<>();
                List<PesawatEntity> pesawat = pesawatService.getByAirportId(airport.getId());
                pesawat.forEach(x ->
                        pesawatResponses.add(PesawatResponse.builder()
                                .id(x.getId())
                                .name(x.getName()).build()));
                data.add(AirportResponse.builder()
                        .id(airport.getId())
                        .airportName(airport.getName())
                        .cityName(airport.getCity().getName())
                        .pesawat(pesawatResponses).build());
            });
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.ACCEPTED.value());
            res.setMessage("Successfully!");
            res.setData(data);
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @Operation(summary = "Get Airport By Id (EndPoint digunakan untuk mendapat airpot detail berdasakan ID airport)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Airport By Id",
                            description = "Endpoint dapat digunakan untuk mendapatkan detail airport berdasarkan airportId," +
                                    "Jika berhasil akan menampilkan data seperti diatas",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 202,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"id\": 1,\n" +
                                    "        \"airportName\": \"Soekarno-Hatta International Airport\",\n" +
                                    "        \"cityName\": \"Jakarta\"\n" +
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
    @GetMapping("/getAirport/{id}")
    public ResponseEntity<ResponseData<Object>> getOne(@PathVariable("id") Long id){
        ResponseData<Object> res = new ResponseData<>();
        try{
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.ACCEPTED.value());
            res.setMessage("Successfully!");
            AirportEntity sample = airportService.getOne(id);
            AirportResponse data = AirportResponse.builder()
                            .id(sample.getId())
                                    .airportName(sample.getName())
                                            .cityName(sample.getCity().getName())
                                                    .build();
            res.setData(data);
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(null);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @Operation(summary = "Delete Airport By Id (EndPoint digunakan untuk menghapus airpot berdasarkan ID airport)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sukses", content = @Content(examples = {
                    @ExampleObject(name = "Delete Airport By Id",
                            description = "Endpoint dapat digunakan untuk mengahapus satu data airport berdasarkan airportId",
                            value = "{\n" +
                                    "    \"success\": true,\n" +
                                    "    \"statusCode\": 202,\n" +
                                    "    \"message\": \"Successfully!\",\n" +
                                    "    \"data\": 1\n" +
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Integer>> delete(@PathVariable("id") Long id){
        ResponseData<Integer> res = new ResponseData<>();
        try{
            res.setSuccess(true);
            res.setStatusCode(HttpStatus.ACCEPTED.value());
            res.setMessage("Successfully!");
            airportService.delete(id);
            res.setData(1);
            return  ResponseEntity.ok().body(res);
        }catch (Exception e){
            res.setSuccess(false);
            res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("Failed!");
            res.setData(0);
            return ResponseEntity.internalServerError().body(res);
        }
    }

}
