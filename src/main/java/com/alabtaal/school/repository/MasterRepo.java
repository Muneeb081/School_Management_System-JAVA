package com.alabtaal.school.repository;

import com.alabtaal.school.entity.AdminEntity;
import com.alabtaal.school.entity.MasterEntity;
import com.alabtaal.school.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterRepo extends JpaRepository<MasterEntity, Long> {

    MasterEntity getById(Long id);
}
