package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CategoryClassEntity;
import org.binaracademy.finalproject.entity.ScheduleTimeEntity;

import java.util.List;

public interface CategoryClassService {

    CategoryClassEntity create(CategoryClassEntity category);
    List<CategoryClassEntity> getAll();
}
