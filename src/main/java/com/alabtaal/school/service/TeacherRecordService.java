
package com.alabtaal.school.service;

import com.alabtaal.school.entity.MasterEntity;
import com.alabtaal.school.entity.TeacherRecordEntity;

import java.util.List;

public interface TeacherRecordService {
    List<TeacherRecordEntity> findAll();
    TeacherRecordEntity findByName(String name);
    Boolean existsByName(String name);
    TeacherRecordEntity findById(Long id);
    void save(TeacherRecordEntity teacherRecord);
    void delete(Long id);
}

