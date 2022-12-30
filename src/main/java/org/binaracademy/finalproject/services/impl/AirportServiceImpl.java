package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.entity.AirportEntity;
import org.binaracademy.finalproject.repositories.AirportRepo;
import org.binaracademy.finalproject.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AirportServiceImpl implements AirportService {

    private static final String error = "Error found {}";

    @Autowired
    AirportRepo airportRepo;

    public AirportEntity create (AirportEntity data){
        try{
            AirportEntity sample = AirportEntity.builder()
                    .name(data.getName())
                    .cityId(data.getCityId())
                    .createAt(LocalDateTime.now()).build();
            log.info("Airport has been created {}", sample.getName());
            return airportRepo.save(sample);
        }catch (Exception e){
            log.error(error, e.getMessage());
            return null;
        }
    }

    public AirportEntity update(Long id, AirportEntity data){
        try{
            AirportEntity sample = getOne(id);
            sample.setName(data.getName());
            sample.setCityId(data.getCityId());
            sample.setUpdateAt(LocalDateTime.now());
            log.info("Airport has been updated {}", sample.getName());
            return airportRepo.save(sample);
        }catch (Exception e){
            log.error(error, e.getMessage());
            return null;
        }
    }

    public AirportEntity getOne(Long id){
        try{
            log.info("Airport has been retrieved!");
            Optional<AirportEntity> sample = airportRepo.findById(id);
            if (sample.isEmpty()){
                log.info("Airport not found!");
                return null;
            }
            return airportRepo.findById(id).get();
        }catch (Exception e){
            log.error(error, e.getMessage());
            return null;
        }
    }

    public List<AirportEntity> getAll(){
        try{
            log.info("Airports have been retrieved");
            return airportRepo.findAll();
        }catch (Exception e){
            log.error(error, e.getMessage());
            return null;
        }
    }

    public void delete(Long id){
        try{
            log.info("Airport has been deleted");
            airportRepo.deleteById(id);
        }catch (Exception e){
            log.error(error, e.getMessage());
        }
    }
}
