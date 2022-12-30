package org.binaracademy.finalproject.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.services.QRCodeService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class QRCodeServiceImpl implements QRCodeService {

    public BufferedImage generateQRCode(String urlText) throws Exception{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        Map<EncodeHintType, Integer> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.QR_MASK_PATTERN, QRCode.NUM_MASK_PATTERNS-6);
        hints.put(EncodeHintType.QR_VERSION, 4);
        bitMatrix = qrCodeWriter.encode(urlText, BarcodeFormat.QR_CODE, 300, 300, hints);
        log.info("call generateQRCode succses");
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}