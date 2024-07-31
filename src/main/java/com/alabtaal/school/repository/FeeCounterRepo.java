package com.alabtaal.school.repository;
import com.alabtaal.school.entity.FeeCounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
    @Repository
    public interface FeeCounterRepo extends JpaRepository<FeeCounterEntity,Long> {

        Boolean existsByFeeCounterId(Long id);

        FeeCounterEntity getByFeeCounterId(Long id);

        List<FeeCounterEntity> findAllByFeeCounterId(Long id);



    }
