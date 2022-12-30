package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.NotificationEntity;
import org.binaracademy.finalproject.repositories.NotificationRepo;
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

@SpringBootTest
public class NotificationServiceTest {
    @Autowired
    NotificationService notificationService;
    @MockBean
    NotificationRepo notificationRepo;

    @BeforeEach
    void setup(){
        Optional<NotificationEntity> sample = Optional.of(new NotificationEntity(1L, "test sample", true, 1L, 1L, null, null, null, null));
        List<NotificationEntity> samples = new ArrayList<>();
        samples.add(sample.get());
        Mockito.when(notificationRepo.findByUserId(1L)).thenReturn(samples);
        Mockito.when(notificationRepo.findById(1L)).thenReturn(sample);
    }

    @Test
    @DisplayName("Create notification")
    void create(){
        NotificationEntity data = new NotificationEntity(1L, "test sample", true, 1L, 1L, null, null, null, null);
        Mockito.when(notificationRepo.save(data)).thenReturn(data);
        assertEquals(data, notificationRepo.save(data));
    }

    @Test
    @DisplayName("Get all notification by userId")
    void findAllByUserId(){
        NotificationEntity data = new NotificationEntity(1L, "test sample", true, 1L, 1L, null, null, null, null);
        List<NotificationEntity> dataList = new ArrayList<>();
        dataList.add(data);
        assertEquals(dataList, notificationService.getAllNotifByUserId(1L));
    }

    @Test
    @DisplayName("Get notification by id")
    void findById(){
        NotificationEntity data = new NotificationEntity(1L, "test sample", true, 1L, 1L, null, null, null, null);
        assertEquals(data, notificationService.getById(1L));
    }
}
