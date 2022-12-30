package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<CityEntity, Long> {

    @Query(value = "SELECT * FROM city WHERE country_id = :countryId", nativeQuery = true)
    List<CityEntity> findCity(Long countryId);

}
