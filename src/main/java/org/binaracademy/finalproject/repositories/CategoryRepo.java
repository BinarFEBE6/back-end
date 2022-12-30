package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.CategoryClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryClassEntity, Long> {
}
