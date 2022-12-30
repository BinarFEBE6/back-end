package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.entity.UserEntity;
import org.binaracademy.finalproject.repositories.GuestRepo;
import org.binaracademy.finalproject.repositories.UserRepo;
import org.binaracademy.finalproject.services.GuestService;
import org.binaracademy.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    public UserEntity create(UserEntity data){
        UserEntity existUser = userRepo.findUserByEmail(data.getEmail());

        if(existUser != null) {
            throw new IllegalStateException("User nama has been taken ");
        }

        try{
            log.info("User has been created");
            UserEntity sample = data;
            sample.setCreateAt(LocalDateTime.now());
            sample.setUpdateAt(LocalDateTime.now());
            return userRepo.save(sample);
        }catch (Exception e){
            log.error("Error found {}", e);
            return null;
        }
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        UserEntity existUser = userRepo.findUserByEmail(email);

        if(existUser == null) {
            throw new IllegalStateException("User not found ");
        }

        try{
            return existUser;
        }catch (Exception e){
            log.error("Error found {}", e);
            return null;
        }
    }

    public void processOAuthPostLogin(String email, String nama) {
        UserEntity existUser = userRepo.findUserByEmail(email);

        if (existUser==null) {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(nama);
            newUser.setEmail(email);
            newUser.setPassword("GOOGLE");
            newUser.setCreateAt(LocalDateTime.now());
            newUser.setUpdateAt(LocalDateTime.now());
            userRepo.save(newUser);
        }
    }

}
