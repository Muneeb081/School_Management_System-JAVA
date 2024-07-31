
package com.alabtaal.school.service;

import com.alabtaal.school.entity.TeacherRecordEntity;
import com.alabtaal.school.repository.TeacherRecordRepo;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherRecordServiceImpl implements TeacherRecordService {

    private final TeacherRecordRepo teacherRecordRepo;
     @Override
    public Boolean existsByName(String name) {

        return teacherRecordRepo.existsByNameIgnoreCase(name);
    }

    @Transactional
    @Override
    public TeacherRecordEntity findById(Long id) {
        TeacherRecordEntity entity = teacherRecordRepo.getById(id);
        Hibernate.initialize(entity.getStudentRecords());
        return entity;
    }

    @Override
    public List<TeacherRecordEntity> findAll() {

        return teacherRecordRepo.findAll();
    }
    @Override
    public TeacherRecordEntity findByName(String name) {

        return teacherRecordRepo.getByNameIgnoreCase(name);
    }

    @Override
    public void save(TeacherRecordEntity teacherRecord) {

        teacherRecordRepo.saveAndFlush(teacherRecord);
    }
    @Override
    public void delete(Long id) {
        teacherRecordRepo.deleteById(id);
    }
}
