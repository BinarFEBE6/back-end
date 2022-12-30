package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepo extends PagingAndSortingRepository<ScheduleEntity, Long> {

    @Query(value = "SELECT * FROM schedules " +
            "WHERE departure_airport = :departureAiport AND arrival_airport = :arrivalAirport AND date_departure >= :date", nativeQuery = true)
    List<ScheduleEntity> findScheduleFromTo(String departureAiport, String arrivalAirport, LocalDate date);

    @Query(value = "SELECT * FROM schedules " +
            "WHERE departure_airport = :departureAiport AND arrival_airport = :arrivalAirport AND date_departure >= :date", nativeQuery = true)
    Page<ScheduleEntity> findScheduleFromToPage(String departureAiport, String arrivalAirport, LocalDate date, Pageable pageable);

}
