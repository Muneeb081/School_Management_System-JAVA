package com.alabtaal.school.service;

import com.alabtaal.school.entity.FeeCounterEntity;
import com.alabtaal.school.entity.MasterEntity;

import java.util.List;

public interface FeeCounterService {
    List<FeeCounterEntity> findAll();
    Boolean existsById(Long id);

    FeeCounterEntity findById(Long id);

    FeeCounterEntity save(FeeCounterEntity feeCounterEntity);

    void delete( FeeCounterEntity feeCounterEntity);
}

