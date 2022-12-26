package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.GuestEntity;
import org.binaracademy.finalproject.repositories.GuestRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GuestServiceTest {
    @MockBean
    GuestRepo guestRepo;
    @Autowired
    GuestService guestService;
    @BeforeEach
    void setup(){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Optional<GuestEntity> sample = Optional.of(new GuestEntity(1L, "Jamal", "Saepudin", LocalDate.parse("1985-08-12", date), "Indonesia", "Indonesia", "OE163HJ792K", LocalDate.parse("2024-10-28", date), "I1YASH9792J", 1L, 1L, null, null, null, null));
        Mockito.when(guestRepo.findById(1L)).thenReturn(sample);
    }

    @Test
    @DisplayName("Create guest")
    void create(){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        GuestEntity data = new GuestEntity(1L, "Jamal", "Saepudin", LocalDate.parse("1985-08-12", date), "Indonesia", "Indonesia", "OE163HJ792K", LocalDate.parse("2024-10-28", date), "I1YASH9792J", 1L, 1L, null, null, null, null);
        Mockito.when(guestRepo.save(data)).thenReturn(data);
        assertEquals(data, guestRepo.save(data));
    }
    @Test
    @DisplayName("Get guest by id")
    void getById(){
        assertEquals("Jamal", guestService.getById(1L).getFirstName());
    }
}
