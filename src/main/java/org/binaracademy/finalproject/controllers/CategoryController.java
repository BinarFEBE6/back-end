package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.dto.Response.CategoryResponse;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.services.CategoryClassService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "CategoryClass", description = "Operation about CategoryClass")
public class CategoryController {
    @Autowired
    CategoryClassService categoryClassService;

    @Operation(summary = "Get category class (Endpoint digunakan untuk mendapatkan Category class)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", content = @Content(examples = {
                    @ExampleObject(name = "List category class",
                            description = "Menampilkan semua kelas kategori",
                            value = "{\n"
                                    + "    \"success\": true,\n"
                                    + "    \"statusCode\": 200,\n"
                                    + "    \"message\": \"Successfully!\",\n"
                                    + "    \"data\": [\n"
                                    + "        {\n"
                                    + "            \"id\": 1,\n"
                                    + "            \"name\": \"economy\"\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "            \"id\": 2,\n"
                                    + "            \"name\": \"bussiness\"\n"
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
    @GetMapping("/getCategories")
    public ResponseEntity<ResponseData<List<CategoryResponse>>> getCategories() {

        ResponseData<List<CategoryResponse>> response = new ResponseData<>();

        try {
            List<CategoryResponse> data = new ArrayList<>();
            categoryClassService.getAll().forEach(category ->
                    data.add(CategoryResponse.builder()
                            .id(category.getId())
                            .name(category.getName()).build()));
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
