package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.NotificationEntity;

import java.util.List;

public interface NotificationService {

    NotificationEntity create(Long orderId);
    NotificationEntity getById(Long notifId);
    List<NotificationEntity> getAllNotifByUserId(Long userId);

}
