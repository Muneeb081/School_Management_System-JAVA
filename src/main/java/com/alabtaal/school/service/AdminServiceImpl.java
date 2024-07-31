package com.alabtaal.school.service;
import com.alabtaal.school.entity.AdminEntity;
import com.alabtaal.school.repository.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final AdminRepo adminRepo;
    @Override
    public List<AdminEntity> findAll() {
        return adminRepo.findAll();
    }

    @Override
    public List<AdminEntity> findAllByName(String name) {
        return adminRepo.findAllByNameIgnoreCase(name);
    }

    @Override
    public AdminEntity save(AdminEntity adminEntity) {
        return adminRepo.saveAndFlush(adminEntity);
    }
    @Override
    public void delete(Long id) {
        adminRepo.deleteById(id);
    }
}
