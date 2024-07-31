package com.alabtaal.school.service;

import com.alabtaal.school.entity.AdminEntity;
import com.alabtaal.school.entity.MasterEntity;
import com.alabtaal.school.entity.TeacherRecordEntity;
import com.alabtaal.school.repository.AdminRepo;
import com.alabtaal.school.repository.MasterRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements MasterService{

    private final MasterRepo masterRepo;


    @Override
    public List<MasterEntity> findAll() {
        return masterRepo.findAll();
    }

    @Transactional
    @Override
    public MasterEntity save(MasterEntity masterEntity) {
        Hibernate.initialize(masterEntity.getStudent());
        return masterRepo.saveAndFlush(masterEntity);
    }

    @Override
    @Transactional
    public MasterEntity findById(Long id) {
        MasterEntity entity = masterRepo.getById(id);
        Hibernate.initialize(entity.getStudent());
        return entity;
    }


    @Override
    public void delete(Long id) {
        masterRepo.deleteById(id);
    }
}
