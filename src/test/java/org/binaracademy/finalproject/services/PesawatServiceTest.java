package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.AirportEntity;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.repositories.PesawatRepo;
import org.binaracademy.finalproject.services.PesawatService;
import org.junit.jupiter.api.BeforeEach;
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
class PesawatServiceTest {

    @MockBean
    private PesawatRepo pesawatRepo;
    @Autowired
    private PesawatService pesawatService;

    PesawatEntity pesawatEntity;
    List<PesawatEntity> pesawatEntityList = new ArrayList<>();

    @BeforeEach
    void setup() {
        pesawatEntity = new PesawatEntity(
                1L, "Garuda", 1L, new AirportEntity(), LocalDateTime.now(), null);
        pesawatEntityList.add(pesawatEntity);
    }

    @Test
    void create() {
        when(pesawatRepo.save(pesawatEntity)).thenReturn(pesawatEntity);
        assertEquals("Garuda", pesawatService.create(pesawatEntity).getName());
        verify(pesawatRepo).save(pesawatEntity);
    }

    @Test
    void getById() {
        when(pesawatRepo.findById(1L)).thenReturn(Optional.of(pesawatEntity));
        assertEquals("Garuda", pesawatService.getById(1L).getName());
        verify(pesawatRepo).findById(1L);
    }

    @Test
    void getAll() {
        when(pesawatRepo.findAll()).thenReturn(pesawatEntityList);
        assertEquals(1, pesawatService.getAll().size());
        verify(pesawatRepo).findAll();
    }

    @Test
    void getByAirportId() {
        when(pesawatRepo.findByAirportId(1L)).thenReturn(pesawatEntityList);
        assertEquals(1, pesawatService.getByAirportId(1L).size());
        verify(pesawatRepo).findByAirportId(1L);
    }
}