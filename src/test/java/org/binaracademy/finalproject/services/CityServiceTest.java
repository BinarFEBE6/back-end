package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CityEntity;
import org.binaracademy.finalproject.entity.CountryEntity;
import org.binaracademy.finalproject.repositories.CityRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CityServiceTest {

    @MockBean
    private CityRepo cityRepo;
    @Autowired
    private CityService cityService;

    List<CityEntity> cityEntityList = new ArrayList<>();
    CountryEntity countryEntity = new CountryEntity();
    Optional<CityEntity> cityEntity;

    @BeforeEach
    void setup() {
        countryEntity = new CountryEntity(
                1L, "Indonesia", LocalDateTime.now(), null);
        cityEntity = Optional.of(
                new CityEntity(1L, "Jakarta", LocalDateTime.now(), null, 1L, countryEntity));
        cityEntityList.add(cityEntity.get());
    }

    @Test
    @DisplayName("Create City")
    void create() {
        when(cityRepo.save(cityEntity.get())).thenReturn(cityEntity.get());
        assertEquals("Jakarta", cityService.create(cityEntity.get()).getName());
        verify(cityRepo).save(cityEntity.get());
    }

    @Test
    @DisplayName("Get all city")
    void getAll() {
        when(cityRepo.findAll()).thenReturn(cityEntityList);
        assertEquals(1, cityService.getAll().size());
        verify(cityRepo).findAll();
    }

    @Test
    @DisplayName("Get all city by id country")
    void getCity() {
        when(cityRepo.findCity(1L)).thenReturn(cityEntityList);
        assertEquals(1, cityService.getCity(1L).size());
        verify(cityRepo).findCity(1L);
    }
}