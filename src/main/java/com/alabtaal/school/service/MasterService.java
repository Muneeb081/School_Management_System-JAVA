package com.alabtaal.school.service;

import com.alabtaal.school.entity.AdminEntity;
import com.alabtaal.school.entity.MasterEntity;

import java.util.List;

public interface MasterService {

    List<MasterEntity> findAll();

    MasterEntity save (MasterEntity masterEntity);

    MasterEntity findById(Long id);

    void delete(Long id);

}
