package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersDetailsRepo extends JpaRepository<UserDetailsEntity, Long> {

    @Query("SELECT ud FROM UserDetailsEntity ud WHERE ud.user_id = ?1")
    Optional<UserDetailsEntity> findUserDetailsByUserId(Long user_id);
}
