package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.binaracademy.finalproject.dto.ResponseData;
import org.binaracademy.finalproject.helper.utility.ImgPatternException;
import org.binaracademy.finalproject.services.UploadImageService;
import org.binaracademy.finalproject.services.impl.UploadImageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Upload Image", description = "Operation about Upload Image")
public class UploadImageController {

    private final UploadImageService uploadImageService;

    @Operation(summary = "(ini test, Tidak digunakan)")
    @PostMapping("/upload")
    public ResponseEntity<ResponseData<String>> uploadImg(@RequestParam("image")MultipartFile multipartFile) {
        ResponseData<String> response = new ResponseData<>();
        try {
            String url = uploadImageService.uploadImg(multipartFile);
            response.setMessage("upload succses");
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setData(url);
            return ResponseEntity.ok().body(response);
        } catch (ImgPatternException | SizeLimitExceededException img){
            response.setMessage(img.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setSuccess(false);
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception io){
            response.setMessage(io.getMessage());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setSuccess(false);
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
