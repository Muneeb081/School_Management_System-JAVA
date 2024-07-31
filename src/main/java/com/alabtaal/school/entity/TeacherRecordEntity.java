package com.alabtaal.school.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Data
@Entity
@Table(schema = "management", name = "teacher_records")
public class TeacherRecordEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "teacher_id")
        private Long teacherId;

        @NotBlank(message = "Name must be entered")
        @Column(name = "name")
        private String name;

        @NotBlank(message = "Subject must be entered")
        @Column(name = "subject")
        private String subject;

        @NotBlank(message = "Department must be entered")
        @Column(name = "department")
        private String department;

        @NotBlank(message = "Designation must be entered")
        @Column(name = "designation")
        private String designation;

        @NotBlank(message = "Qualification must be entered")
        @Column(name = "qualification")
        private String qualification;

        @OneToMany(mappedBy = "teacherRecord", fetch = FetchType.EAGER)
        private List<StudentRecordEntity> studentRecords;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private MasterEntity master;
}
