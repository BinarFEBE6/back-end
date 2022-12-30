package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<CountryEntity, Long> {
}
