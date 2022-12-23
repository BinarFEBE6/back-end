package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.helper.utility.ImgPatternException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadImageService {

    String uploadImg(MultipartFile multipartFile) throws IOException, ImgPatternException;

}
