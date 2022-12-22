package org.binaracademy.finalproject.services;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {

    String uploadImg(MultipartFile multipartFile);

}
