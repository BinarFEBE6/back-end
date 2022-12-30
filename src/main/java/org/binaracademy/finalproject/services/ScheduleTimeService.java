package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.CountryEntity;
import org.binaracademy.finalproject.entity.ScheduleTimeEntity;

import java.util.List;

public interface ScheduleTimeService {

    ScheduleTimeEntity create(ScheduleTimeEntity schedule);
    List<ScheduleTimeEntity> getAll();
}
