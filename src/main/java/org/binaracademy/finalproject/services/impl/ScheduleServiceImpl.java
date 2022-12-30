package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.binaracademy.finalproject.repositories.ScheduleRepo;
import org.binaracademy.finalproject.services.ScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public ScheduleEntity create(ScheduleEntity schedule) {
        try {
            schedule.setCreateAt(LocalDateTime.now());
            ScheduleEntity scheduleEntity = scheduleRepo.save(schedule);
            log.info("Schedule has been created : {}", schedule.getId());
            return scheduleEntity;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return  null;
        }
    }

    @Override
    public List<ScheduleEntity> getAll() {
        try {
            List<ScheduleEntity> scheduleEntities = (List<ScheduleEntity>) scheduleRepo.findAll();
            log.info("call getAll succses");
            return scheduleEntities;
        } catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public ScheduleEntity getOneSchedule(Long id) {
        try {
            Optional<ScheduleEntity> schedule = scheduleRepo.findById(id);
            log.info("call getOneSchedule succses");
            return schedule.orElse(null);
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    @Override
    public List<ScheduleEntity> getFromTo(String departureAiport, String arrivalAirport) {
        try {
            List<ScheduleEntity> scheduleEntities = scheduleRepo.findScheduleFromTo(departureAiport, arrivalAirport, LocalDate.now());
            if (scheduleEntities.isEmpty()) {
                return Collections.emptyList();
            }
            log.info("call getFromTo succses");
            return scheduleEntities;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Iterable<ScheduleEntity> getPageFromTo(String departureAiport, String arrivalAirport, Pageable pageable) {
        try {
            Iterable<ScheduleEntity> scheduleEntities = scheduleRepo.findScheduleFromToPage(
                    departureAiport, arrivalAirport, LocalDate.now(), pageable);
            if (scheduleEntities == null) {
                return Collections.emptyList();
            }
            log.info("call getPageFromTo succses");
            return scheduleEntities;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

}
