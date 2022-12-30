package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<SeatEntity, Long> {

    @Query(value = "SELECT * " +
                    "FROM seats s " +
                    "WHERE NOT EXISTS " +
                    "(SELECT * FROM ticket t " +
                    "WHERE t.seat_id=s.seat_id AND t.schedule_id=:scheduleId)", nativeQuery = true)
    List<SeatEntity> findSeatAvailable(Long scheduleId);

}
