package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.data.PesawatData;
import org.binaracademy.finalproject.entity.PesawatEntity;

import java.util.List;

public interface PesawatService {
    public PesawatEntity create(PesawatEntity data);
    public List<PesawatEntity> getAll();
    public List<PesawatEntity> getByAirportId(Long id);
    public PesawatEntity getById(Long id);
}
