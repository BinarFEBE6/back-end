package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.ScheduleTimeEntity;
import org.binaracademy.finalproject.repositories.ScheduleTimeRepo;
import org.binaracademy.finalproject.services.ScheduleTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduleTimeServiceTest {

    @MockBean
    private ScheduleTimeRepo scheduleTimeRepo;
    @Autowired
    private ScheduleTimeService scheduleTimeService;

    ScheduleTimeEntity scheduleTimeEntity;
    List<ScheduleTimeEntity> scheduleTimeEntityList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        scheduleTimeEntity = new ScheduleTimeEntity(
                1L, "sunday", LocalTime.now(), LocalTime.now().plusHours(5L),
                LocalDateTime.now(), null);
        scheduleTimeEntityList.add(scheduleTimeEntity);
    }

    @Test
    @DisplayName("Create Schedule Time")
    void create() {
        when(scheduleTimeRepo.save(scheduleTimeEntity)).thenReturn(scheduleTimeEntity);
        assertEquals("sunday", scheduleTimeService.create(scheduleTimeEntity).getDay());
        verify(scheduleTimeRepo).save(scheduleTimeEntity);
    }

    @Test
    @DisplayName("Get All Schedule Time")
    void getAll() {
        when(scheduleTimeRepo.findAll()).thenReturn(scheduleTimeEntityList);
        assertEquals(1, scheduleTimeService.getAll().size());
        verify(scheduleTimeRepo).findAll();
    }
}