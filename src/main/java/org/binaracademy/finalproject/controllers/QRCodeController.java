package org.binaracademy.finalproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.binaracademy.finalproject.services.QRCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "QR Code", description = "Operation about QR Code")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @Operation(summary = "Get QR Code (EndPoint digunakan untuk generate QR Code ke link generate invoice order berdasarkan ID order)")
    @GetMapping(value = "/QRcode/{orderId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingQRCode(@PathVariable Long orderId) throws Exception{
        String url = "https://binar-academy-terbangin.herokuapp.com/api/generateOrder/"+orderId;
        return succesResponse(qrCodeService.generateQRCode(url));
    }

    private ResponseEntity<BufferedImage> succesResponse(BufferedImage generateQRCode) {
        return new ResponseEntity<>(generateQRCode, HttpStatus.OK);
    }

}
