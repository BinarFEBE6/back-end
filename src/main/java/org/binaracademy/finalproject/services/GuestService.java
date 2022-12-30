package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.entity.GuestEntity;

public interface GuestService {
    public GuestEntity create(GuestEntity data);
    GuestEntity getById(Long guestId);
}
