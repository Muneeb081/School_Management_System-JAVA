package com.alabtaal.school.service;

import com.alabtaal.school.entity.AdminEntity;

import java.util.List;

public interface AdminService {

    List<AdminEntity> findAll();
    List<AdminEntity> findAllByName (String name);
    AdminEntity save (AdminEntity adminEntity);

    void delete(Long id);

}
