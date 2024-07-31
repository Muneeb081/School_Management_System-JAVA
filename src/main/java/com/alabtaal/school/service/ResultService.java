package com.alabtaal.school.service;

import com.alabtaal.school.entity.ResultEntity;

import java.util.List;

public interface ResultService {
    List<ResultEntity> findAll();

    ResultEntity save(ResultEntity entity);
    ResultEntity findById(Long id);
    void delete(ResultEntity entity);
}

