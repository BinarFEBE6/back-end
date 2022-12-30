package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.PesawatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PesawatRepo extends JpaRepository<PesawatEntity, Long> {
    List<PesawatEntity> findByAirportId(Long airportId);
}
