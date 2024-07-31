package com.alabtaal.school.repository;

import com.alabtaal.school.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepo extends JpaRepository<ResultEntity, Long> {

    List<ResultEntity> findAllByResultId(Long id);

    ResultEntity getByResultId(Long id);

}
