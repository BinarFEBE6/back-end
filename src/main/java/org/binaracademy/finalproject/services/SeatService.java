package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.SeatEntity;

import java.util.List;

public interface SeatService {

    SeatEntity create (SeatEntity seatEntity);
    List<SeatEntity> createAll(List<SeatEntity> seatEntities);
    List<SeatEntity> getAll();
    List<SeatEntity> getSeatAvail(Long scheduleId);

}
