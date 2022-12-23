package org.binaracademy.finalproject.services.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.helper.utility.ImgPatternException;
import org.binaracademy.finalproject.services.UploadImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadImageServiceImpl implements UploadImageService {

    @Value("${CLOUDINARY_URL}")
    private String url;
    private final Path root = Paths.get("src/main/resources/data");
    private static final String IMG_PATTERN = "(.*/)*.+\\.(png|jpg|jpeg|PNG|JPG|JPEG)$";

    public boolean validate(String imgName){
        return Pattern.matches(IMG_PATTERN,imgName);
    }

    public String uploadImg(MultipartFile multipartFile) throws IOException, ImgPatternException {
            if (!validate(multipartFile.getOriginalFilename())){
                throw new ImgPatternException("Please Upload Image Extention File");
            }
            Files.copy(multipartFile.getInputStream(),
                    this.root.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename())));
            String slash = "/";
            Path path = Path.of(this.root + slash + multipartFile.getOriginalFilename());
            File file = new File(path.toString());
            Cloudinary cloudinary = new Cloudinary(url);
            Map<String, Object> upload = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            String imgUrl = upload.get("url").toString();
            Files.delete(path);
            log.info("upload image sucsess with image name : {}",multipartFile.getOriginalFilename());
            return imgUrl;
    }

}
