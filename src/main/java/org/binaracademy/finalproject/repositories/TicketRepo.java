package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepo extends JpaRepository<TicketEntity, Long> {

    @Query(value = "SELECT order_id FROM orders WHERE schedule_id = :scheduleId AND email_user = :userEmail", nativeQuery = true)
    Long findOrderIdByScheduleIdAndUsername(Long scheduleId, String userEmail);

    Optional<TicketEntity> findByGuestId(Long guestId);

}
