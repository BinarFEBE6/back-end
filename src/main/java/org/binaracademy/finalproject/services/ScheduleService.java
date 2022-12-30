package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    ScheduleEntity create(ScheduleEntity schedule);
    List<ScheduleEntity> getAll();
    ScheduleEntity getOneSchedule(Long id);
    List<ScheduleEntity> getFromTo(String departureAiport, String arrivalAirport);
    Iterable<ScheduleEntity> getPageFromTo(String departureAiport, String arrivalAirport, Pageable pageable);

}
