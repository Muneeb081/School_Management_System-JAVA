package com.alabtaal.school.service;
import com.alabtaal.school.entity.StudentRecordEntity;
import com.alabtaal.school.repository.StudentRecordRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class StudentRecordServiceImpl implements StudentRecordService {

    private final StudentRecordRepo studentRecordRepo;


    @Override
    public List<StudentRecordEntity> findAll() {
        return studentRecordRepo.findAll();
    }

    @Override
    public StudentRecordEntity findByName(String name) {
        return studentRecordRepo.getByNameIgnoreCase(name);
    }

    @Override
    public StudentRecordEntity findById(Long id) {
        return studentRecordRepo.getByStudentId(id);
    }

    @Override
    public StudentRecordEntity save (StudentRecordEntity studentRecord) {
        return studentRecordRepo.saveAndFlush(studentRecord);
    }

    @Override
    public void delete(Long id) {
        studentRecordRepo.deleteById(id);

    }
}
