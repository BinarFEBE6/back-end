package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.CityEntity;
import org.binaracademy.finalproject.repositories.CityRepo;
import org.binaracademy.finalproject.services.CityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepo cityRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public CityEntity create(CityEntity city) {
        try {
            city.setCreateAt(LocalDateTime.now());
            CityEntity cityEntity = cityRepo.save(city);
            log.info("City has been created : {}", city.getName());
            return cityEntity;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return  null;
        }
    }

    @Override
    public List<CityEntity> getAll() {
        try {
            List<CityEntity> cityEntities = cityRepo.findAll();
            log.info("call getAll succses");
            return cityEntities;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<CityEntity> getCity(Long countryId) {
        try {
            List<CityEntity> cityEntities = cityRepo.findCity(countryId);
            log.info("call getCity succses");
            return cityEntities;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

}
