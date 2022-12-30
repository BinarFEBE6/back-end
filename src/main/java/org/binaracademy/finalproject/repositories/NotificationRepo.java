package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUserId(Long userId);
    @Query(value = "SELECT g.user_id FROM orders o, ticket t, guests g\n" +
            "WHERE o.order_id = t.order_id AND t.guest_id = g.guest_id AND o.order_id = :orderId", nativeQuery = true)
    List<Long> findUserId(Long orderId);

}
