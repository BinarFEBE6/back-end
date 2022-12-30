package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepo extends JpaRepository<GuestEntity, Long> {
}
