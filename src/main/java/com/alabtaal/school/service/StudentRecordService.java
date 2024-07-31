package com.alabtaal.school.service;
import com.alabtaal.school.entity.StudentRecordEntity;

import java.util.List;

public interface StudentRecordService {

    List<StudentRecordEntity> findAll();

    StudentRecordEntity findByName(String name);
    StudentRecordEntity findById(Long id);
    StudentRecordEntity save(StudentRecordEntity studentRecord);


    void delete(Long id);

}
