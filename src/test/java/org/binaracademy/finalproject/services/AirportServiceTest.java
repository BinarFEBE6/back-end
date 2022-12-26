package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.AirportEntity;
import org.binaracademy.finalproject.repositories.AirportRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class AirportServiceTest {
    @MockBean
    AirportRepo airportRepo;
    @Autowired
    AirportService airportService;
    @BeforeEach
    void setup(){
        List<AirportEntity> allData = new ArrayList<>();
        Optional<AirportEntity> data = Optional.of(new AirportEntity(1L, "Soekarno-Hatta International Airport", 1L, null, null, null));
        allData.add(data.get());
        Mockito.when(airportRepo.findById(1L)).thenReturn(data);
        Mockito.when(airportRepo.findAll()).thenReturn(allData);
    }

    @Test
    @DisplayName("Create airport")
    void create(){
        AirportEntity airport = new AirportEntity(1L, "Soekarno-Hatta International Airport", 1L, null, null, null);
        Mockito.when(airportRepo.save(airport)).thenReturn(airport);
        assertEquals(airport, airportRepo.save(airport));
    }
    @Test
    @DisplayName("Update airport")
    void update(){
        AirportEntity airport = new AirportEntity(1L, "Soekarno-Hatta Airport", 1L, null, null, null);
        Mockito.when(airportService.update(1L, airport)).thenReturn(airport);
        assertEquals(airport, airportService.update(1L, airport));
    }
    @Test
    @DisplayName("Get by Id")
    void getById(){
        assertEquals(1L, airportService.getOne(1L).getCityId());
    }
    @Test
    @DisplayName("Get all")
    void getAll(){
        List<AirportEntity> allData = new ArrayList<>();
        AirportEntity data = new AirportEntity(1L, "Soekarno-Hatta International Airport", 1L, null, null, null);
        allData.add(data);
        assertEquals(allData, airportService.getAll());
    }
    @Test
    @DisplayName("Delete airport")
    void delete(){
        airportService.delete(1L);
        verify(airportRepo, times(1)).deleteById(1L);
    }
}
