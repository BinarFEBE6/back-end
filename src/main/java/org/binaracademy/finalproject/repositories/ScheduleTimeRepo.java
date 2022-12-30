package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.ScheduleTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleTimeRepo extends JpaRepository<ScheduleTimeEntity, Long> {
}
