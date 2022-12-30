package org.binaracademy.finalproject.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.SeatEntity;
import org.binaracademy.finalproject.repositories.SeatRepo;
import org.binaracademy.finalproject.services.SeatService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepo seatRepo;
    private static final String ERROR_FOUND = "Error found : {}";

    @Override
    public SeatEntity create(SeatEntity seatEntity) {
        try {
            SeatEntity seat = seatRepo.save(seatEntity);
            log.info("call create seat succses");
            return seat;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return null;
        }
    }

    @Override
    public List<SeatEntity> createAll(List<SeatEntity> seatEntities) {
        try {
            List<SeatEntity> seats = seatRepo.saveAll(seatEntities);
            log.info("call createAll seat succses");
            return seats;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<SeatEntity> getAll() {
        try {
            List<SeatEntity> seats = seatRepo.findAll();
            log.info("call getAll seat succses");
            return seats;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<SeatEntity> getSeatAvail(Long scheduleId) {
        try {
            List<SeatEntity> seats = seatRepo.findSeatAvailable(scheduleId);
            log.info("call getSeatAvail seat succses");
            return seats;
        }catch (Exception e){
            log.error(ERROR_FOUND, e.getMessage());
            return Collections.emptyList();
        }
    }
}
