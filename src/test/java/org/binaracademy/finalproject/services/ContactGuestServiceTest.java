package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.binaracademy.finalproject.repositories.ContactGuestRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ContactGuestServiceTest {
    @MockBean
    ContactGuestRepo contactGuestRepo;

    @Test
    @DisplayName("Create contact guest")
    void create(){
        ContactGuestEntity data = new ContactGuestEntity(Long.valueOf(1), "Jamal", "Saepudin", "085678901234", "jamal@gmail.com", null, null);
        Mockito.when(contactGuestRepo.save(data)).thenReturn(data);
        assertEquals(data, contactGuestRepo.save(data));
    }
}
