package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.dto.Request.UserDetailsRequest;
import org.binaracademy.finalproject.entity.SeatEntity;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.helper.utility.ImgPatternException;
import org.binaracademy.finalproject.helper.utility.UserNotFoundException;
import org.binaracademy.finalproject.repositories.UsersDetailsRepo;
import org.binaracademy.finalproject.services.UploadImageService;
import org.binaracademy.finalproject.services.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UsersDetailsServiceImpl implements UsersDetailsService {

    @Autowired
    private UsersDetailsRepo userDetailRepo;
    @Autowired
    private UploadImageService uploadImageService;

    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public UserDetailsEntity create(UserDetailsEntity userdetailsEntity) {
        try{
            UserDetailsEntity userDetails = userDetailRepo.save(userdetailsEntity);
            log.info("User Details has been created");
            return userDetails;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    @Override
    public UserDetailsEntity update(UserDetailsEntity userdetailsEntity) {
        try{
            return userDetailRepo.save(userdetailsEntity);
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    @Override
    public UserDetailsEntity findByUserid(Long user_id) {
        UserDetailsEntity existUserDetails = userDetailRepo.findUserDetailsByUserId(user_id).get();

        return existUserDetails;
    }

    @Override
    public UserDetailsEntity update2(UserDetailsRequest data, Long userId) throws UserNotFoundException, ImgPatternException, IOException {
        Optional<UserDetailsEntity> sample = userDetailRepo.findUserDetailsByUserId(userId);
        if (sample.isEmpty()){
            log.info("User Not Found with id {} :", userId);
            throw new UserNotFoundException("User Not Found");
        }

        UserDetailsEntity userDetailsTemp = sample.get();
        String profile = userDetailsTemp.getUser().getProfile();

        if (!"".equals(data.getPicture().getOriginalFilename())){
            profile = uploadImageService.uploadImg(data.getPicture());
        }

        userDetailsTemp.setDisplayName(data.getDisplayName());
        userDetailsTemp.setBirthDate(LocalDate.parse(data.getBirthDate()));
        userDetailsTemp.setAddress(data.getAddress());
        userDetailsTemp.setGender(data.getGender());
        userDetailsTemp.getUser().setProfile(profile);
        userDetailsTemp.getUser().setUpdateAt(LocalDateTime.now());
        userDetailsTemp.setUpdateAt(LocalDateTime.now());

        UserDetailsEntity userDetails = userDetailRepo.save(userDetailsTemp);
        log.info("Update user detail with user {} succes :",userDetails.getUser().getUsername());
        return userDetails;
    }

}
