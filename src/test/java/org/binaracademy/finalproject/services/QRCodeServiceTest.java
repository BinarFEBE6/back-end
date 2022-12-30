package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.services.QRCodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QRCodeServiceTest {

    @Autowired
    private QRCodeService qrCodeService;
    String url = "https://www.youtube.com/";

    @Test
    @DisplayName("Generate QrCode")
    void generateQRCode() throws Exception {
        assertNotNull(qrCodeService.generateQRCode(url));
    }
}