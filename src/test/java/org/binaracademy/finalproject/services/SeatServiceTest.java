package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.SeatEntity;
import org.binaracademy.finalproject.repositories.SeatRepo;
import org.binaracademy.finalproject.services.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SeatServiceTest {

    @MockBean
    private SeatRepo seatRepo;
    @Autowired
    private SeatService seatService;

    SeatEntity seatEntity;
    List<SeatEntity> seatEntityList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        seatEntity = new SeatEntity(1L, "001");
        seatEntityList.add(seatEntity);
    }

    @Test
    @DisplayName("Create Seat")
    void create() {
        when(seatRepo.save(seatEntity)).thenReturn(seatEntity);
        assertEquals("001", seatService.create(seatEntity).getSeatName());
        verify(seatRepo).save(seatEntity);
    }

    @Test
    @DisplayName("Create All Seat")
    void createAll() {
        when(seatRepo.saveAll(seatEntityList)).thenReturn(seatEntityList);
        assertEquals(1, seatService.createAll(seatEntityList).size());
        verify(seatRepo).saveAll(seatEntityList);
    }

    @Test
    @DisplayName("Get All Seat")
    void getAll() {
        when(seatRepo.findAll()).thenReturn(seatEntityList);
        assertEquals(1, seatService.getAll().size());
        verify(seatRepo).findAll();
    }

    @Test
    @DisplayName("Get Seat Avilable")
    void getSeatAvail() {
        when(seatRepo.findSeatAvailable(1L)).thenReturn(seatEntityList);
        assertEquals(1, seatService.getSeatAvail(1L).size());
        verify(seatRepo).findSeatAvailable(1L);
    }
}