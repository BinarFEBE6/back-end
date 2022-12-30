package org.binaracademy.finalproject.services;

import java.awt.image.BufferedImage;

public interface QRCodeService {

    BufferedImage generateQRCode(String urlText)throws Exception;

}
