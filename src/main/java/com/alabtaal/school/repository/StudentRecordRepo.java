package com.alabtaal.school.repository;

import com.alabtaal.school.entity.StudentRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRecordRepo extends JpaRepository <StudentRecordEntity, Long> {


    List<StudentRecordEntity> findAllByNameIgnoreCase(String name);


    StudentRecordEntity getByNameIgnoreCase(String name);

    StudentRecordEntity getByStudentId(Long id);
    List<StudentRecordEntity> findAllByRollNumber(Long rollNumber);


}
