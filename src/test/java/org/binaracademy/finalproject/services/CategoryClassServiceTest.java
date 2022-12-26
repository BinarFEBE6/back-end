package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CategoryClassEntity;
import org.binaracademy.finalproject.repositories.CategoryRepo;
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
public class CategoryClassServiceTest {
    @MockBean
    CategoryRepo categoryRepo;
    @Autowired
    CategoryClassService categoryClassService;
    @BeforeEach
    void setup(){
        Optional<CategoryClassEntity> sample = Optional.of(new CategoryClassEntity(Long.valueOf(1), "Economy", null, null));
        List<CategoryClassEntity> sampleList = new ArrayList<>();
        sampleList.add(sample.get());
        Mockito.when(categoryRepo.findById(Long.valueOf(1))).thenReturn(sample);
        Mockito.when(categoryRepo.findAll()).thenReturn(sampleList);
    }
    @Test
    @DisplayName("Create category class")
    void create(){
        CategoryClassEntity data = new CategoryClassEntity(Long.valueOf(1), "Economy", null, null);
        Mockito.when(categoryRepo.save(data)).thenReturn(data);
        assertEquals(data, categoryRepo.save(data));
    }
    @Test
    @DisplayName("Get all category class")
    void getAll(){
        CategoryClassEntity data = new CategoryClassEntity(Long.valueOf(1), "Economy", null, null);
        List<CategoryClassEntity> dataList = new ArrayList<>();
        dataList.add(data);
        assertEquals(dataList, categoryClassService.getAll());
    }
}
