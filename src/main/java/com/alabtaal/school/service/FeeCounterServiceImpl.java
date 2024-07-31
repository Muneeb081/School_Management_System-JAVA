package com.alabtaal.school.service;

import com.alabtaal.school.entity.FeeCounterEntity;
import com.alabtaal.school.entity.StudentRecordEntity;
import com.alabtaal.school.entity.TeacherRecordEntity;
import com.alabtaal.school.repository.FeeCounterRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeeCounterServiceImpl implements FeeCounterService {

    private final FeeCounterRepo feeCounterRepo;

    @Override
    public List<FeeCounterEntity> findAll() {
        return feeCounterRepo.findAll();
    }

    @Override
    public Boolean existsById(Long id) {
        return feeCounterRepo.existsByFeeCounterId(id);
    }

    @Override
    public FeeCounterEntity findById(Long id) {
        return feeCounterRepo.getByFeeCounterId(id) ;
    }

    @Override
    @Transactional
    public FeeCounterEntity save(FeeCounterEntity feeCounterEntity) {
        Hibernate.initialize(feeCounterEntity.getStudentRecord());
        return feeCounterRepo.saveAndFlush(feeCounterEntity);
    }



    @Override
    public void delete(FeeCounterEntity feeCounterEntity) {
        feeCounterRepo.delete(feeCounterEntity);
    }
}
