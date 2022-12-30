package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.data.AirportData;
import org.binaracademy.finalproject.entity.AirportEntity;

import java.util.List;

public interface AirportService {
    public AirportEntity create(AirportEntity data);
    public AirportEntity update (Long id, AirportEntity data);
    public AirportEntity getOne(Long id);
    public List<AirportEntity> getAll();
    void delete(Long id);

}
