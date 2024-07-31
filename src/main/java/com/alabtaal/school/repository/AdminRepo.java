package com.alabtaal.school.repository;
import com.alabtaal.school.entity.AdminEntity;
import com.alabtaal.school.repository.AdminRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface AdminRepo extends JpaRepository<AdminEntity, Long> {
        List<AdminEntity> findAllByNameIgnoreCase (String name);

        List<AdminEntity> findAllByPasswordIgnoreCase (String password);
    }


