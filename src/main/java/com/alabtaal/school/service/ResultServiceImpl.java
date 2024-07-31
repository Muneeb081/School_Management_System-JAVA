package com.alabtaal.school.service;

import com.alabtaal.school.entity.FeeCounterEntity;
import com.alabtaal.school.entity.ResultEntity;
import com.alabtaal.school.entity.TeacherRecordEntity;
import com.alabtaal.school.repository.ResultRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepo resultRepo;

    @Override
    public List<ResultEntity> findAll() {
        return resultRepo.findAll();
    }

    @Transactional
    @Override
    public ResultEntity save(ResultEntity entity) {

        Hibernate.initialize(entity.getStudentRecord());
        return resultRepo.saveAndFlush(entity);
    }

    @Override
    public ResultEntity findById(Long id) {

        return resultRepo.getByResultId(id);
    }

    @Override
    public void delete(ResultEntity resultEntity) {
        resultRepo.delete(resultEntity);
    }
}
