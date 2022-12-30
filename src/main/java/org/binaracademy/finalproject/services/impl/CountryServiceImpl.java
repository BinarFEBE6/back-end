package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.CountryEntity;
import org.binaracademy.finalproject.repositories.CountryRepo;
import org.binaracademy.finalproject.services.CountryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public CountryEntity create(CountryEntity country) {
        try {
            country.setCreateAt(LocalDateTime.now());
            CountryEntity countryEntity = countryRepo.save(country);
            log.info("Country has been created : {}", country.getName());
            return countryEntity;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return  null;
        }
    }

    @Override
    public List<CountryEntity> getAll() {
        try {
            List<CountryEntity> countryEntities = countryRepo.findAll();
            log.info("call getAll succses");
            return countryEntities;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<CountryEntity> createAll(List<CountryEntity> countryList) {
        return countryRepo.saveAll(countryList);
    }

    @Override
    public CountryEntity getOneCountry(Long id) {
        Optional<CountryEntity> country = countryRepo.findById(id);
        if (country.isPresent()) {
            return country.get();
        }
        return null;
    }

}
