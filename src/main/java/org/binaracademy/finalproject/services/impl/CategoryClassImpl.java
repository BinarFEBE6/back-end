package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.CategoryClassEntity;
import org.binaracademy.finalproject.entity.ScheduleTimeEntity;
import org.binaracademy.finalproject.repositories.CategoryRepo;
import org.binaracademy.finalproject.repositories.ScheduleTimeRepo;
import org.binaracademy.finalproject.services.CategoryClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CategoryClassImpl implements CategoryClassService {

    @Autowired
    CategoryRepo categoryRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public CategoryClassEntity create(CategoryClassEntity category) {
        try {
            category.setCreateAt(LocalDateTime.now());
            CategoryClassEntity categoryEntity = categoryRepo.save(category);
            log.info("City has been created : {}", category.getName());
            return categoryEntity;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return  null;
        }
    }

    @Override
    public List<CategoryClassEntity> getAll() {
        try {
            List<CategoryClassEntity> categoryEntity = categoryRepo.findAll();
            log.info("call getAll succses");
            return categoryEntity;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }
}
