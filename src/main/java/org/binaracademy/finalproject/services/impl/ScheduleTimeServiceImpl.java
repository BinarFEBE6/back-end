package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.CityEntity;
import org.binaracademy.finalproject.entity.CountryEntity;
import org.binaracademy.finalproject.entity.ScheduleTimeEntity;
import org.binaracademy.finalproject.repositories.CityRepo;
import org.binaracademy.finalproject.repositories.ScheduleTimeRepo;
import org.binaracademy.finalproject.services.ScheduleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ScheduleTimeServiceImpl implements ScheduleTimeService {
    @Autowired
    ScheduleTimeRepo schTimeRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public ScheduleTimeEntity create(ScheduleTimeEntity schedule) {
        try {
            schedule.setCreateAt(LocalDateTime.now());
            ScheduleTimeEntity schTimeEntity = schTimeRepo.save(schedule);
            log.info("City has been created : {}", schedule.getDay() + " " + schedule.getDepatureTime() + " " + schedule.getDepatureTime());
            return schTimeEntity;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return  null;
        }
    }

    @Override
    public List<ScheduleTimeEntity> getAll() {
        try {
            List<ScheduleTimeEntity> cityEntities = schTimeRepo.findAll();
            log.info("call getAll succses");
            return cityEntities;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }
}
