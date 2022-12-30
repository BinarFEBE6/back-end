package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CountryEntity;
import org.binaracademy.finalproject.repositories.CountryRepo;
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
class CountryServiceTest {

    @MockBean
    private CountryRepo countryRepo;
    @Autowired
    CountryService countryService;

    List<CountryEntity> countryEntityList = new ArrayList<>();
    CountryEntity countryEntity;

    @BeforeEach
    void setUp() {
        countryEntity = new CountryEntity(1l, "Indonesia", LocalDateTime.now(), null);
        countryEntityList.add(countryEntity);
    }

    @Test
    @DisplayName("Create Country")
    void create() {
        when(countryRepo.save(countryEntity)).thenReturn(countryEntity);
        assertEquals("Indonesia", countryService.create(countryEntity).getName());
        verify(countryRepo).save(countryEntity);
    }

    @Test
    @DisplayName("Get All Country")
    void getAll() {
        when(countryRepo.findAll()).thenReturn(countryEntityList);
        assertEquals(1, countryService.getAll().size());
        verify(countryRepo).findAll();
    }

    @Test
    @DisplayName("Create All Country")
    void createAll() {
        when(countryRepo.saveAll(countryEntityList)).thenReturn(countryEntityList);
        assertEquals(1, countryService.createAll(countryEntityList).size());
        verify(countryRepo).saveAll(countryEntityList);
    }

    @Test
    @DisplayName("Get One Country")
    void getOneCountry() {
        when(countryRepo.findById(1L)).thenReturn(Optional.of(countryEntity));
        assertEquals("Indonesia", countryService.getOneCountry(1L).getName());
        verify(countryRepo).findById(1L);
    }
}