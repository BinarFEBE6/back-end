package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepo extends JpaRepository<AirportEntity, Long> {
}
