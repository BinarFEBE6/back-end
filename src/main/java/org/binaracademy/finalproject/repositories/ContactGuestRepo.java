package org.binaracademy.finalproject.repositories;

import org.binaracademy.finalproject.entity.ContactGuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactGuestRepo extends JpaRepository<ContactGuestEntity, Long> {
}
