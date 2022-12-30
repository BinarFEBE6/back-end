package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.ERole;
import org.binaracademy.finalproject.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole name);
}