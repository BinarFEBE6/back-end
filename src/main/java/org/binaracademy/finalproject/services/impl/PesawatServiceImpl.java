package org.binaracademy.finalproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.binaracademy.finalproject.data.PesawatData;
import org.binaracademy.finalproject.entity.PesawatEntity;
import org.binaracademy.finalproject.repositories.PesawatRepo;
import org.binaracademy.finalproject.services.PesawatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PesawatServiceImpl implements PesawatService {

    @Autowired
    PesawatRepo pesawatRepo;

    public PesawatEntity create(PesawatEntity data){
        try{
            PesawatEntity sample = pesawatRepo.save(data);
            log.info("Pesawat has been created {}", sample.getName());
            return sample;
        }catch (Exception e){
            log.error("Error found {}", e.getMessage());
            return null;
        }
    }

    @Override
    public PesawatEntity getById(Long id) {
        Optional<PesawatEntity> data = pesawatRepo.findById(id);
        if(data.isEmpty()){
            return null;
        }
        return data.get();
    }

    @Override
    public List<PesawatEntity> getAll() {
        return pesawatRepo.findAll();
    }

    @Override
    public List<PesawatEntity> getByAirportId(Long id) {
        return pesawatRepo.findByAirportId(id);
    }
}
