package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CityEntity;

import java.util.List;

public interface CityService {

    CityEntity create(CityEntity city);
    List<CityEntity> getAll();
    List<CityEntity> getCity(Long countryId);

}
