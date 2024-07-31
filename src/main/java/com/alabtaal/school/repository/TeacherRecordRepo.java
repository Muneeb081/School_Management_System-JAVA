package com.alabtaal.school.repository;

import com.alabtaal.school.entity.MasterEntity;
import com.alabtaal.school.entity.TeacherRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRecordRepo extends JpaRepository<TeacherRecordEntity, Long> {

    Boolean existsByNameIgnoreCase(String name);
    TeacherRecordEntity getByNameIgnoreCase(String name);
    TeacherRecordEntity getById(Long id);




}
