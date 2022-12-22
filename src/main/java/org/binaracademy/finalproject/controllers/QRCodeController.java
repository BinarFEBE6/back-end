package org.binaracademy.finalproject.controllers;

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
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @GetMapping(value = "/QRcode/{orderId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingQRCode(@PathVariable Long orderId) throws Exception{
        String url = "https://binar-academy-terbangin.herokuapp.com/api/generateOrder/"+orderId;
        return succesResponse(qrCodeService.generateQRCode(url));
    }

    private ResponseEntity<BufferedImage> succesResponse(BufferedImage generateQRCode) {
        return new ResponseEntity<>(generateQRCode, HttpStatus.OK);
    }

}
