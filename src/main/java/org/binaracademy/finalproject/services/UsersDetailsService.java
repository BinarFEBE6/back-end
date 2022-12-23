package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Request.UserDetailsRequest;
import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.binaracademy.finalproject.helper.utility.ImgPatternException;
import org.binaracademy.finalproject.helper.utility.UserNotFoundException;

import java.io.IOException;

public interface UsersDetailsService {

    UserDetailsEntity create (UserDetailsEntity userdetailsEntity);
    UserDetailsEntity update (UserDetailsEntity userdetailsEntity);
    UserDetailsEntity findByUserid (Long user_id);
    UserDetailsEntity update2(UserDetailsRequest data, Long userId) throws UserNotFoundException, ImgPatternException, IOException;
}
