package com.alabtaal.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(schema = "management", name = "student_records", uniqueConstraints = {
        @UniqueConstraint(name = "student_records_uk1", columnNames = "roll_number")
})
public class StudentRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @NotBlank(message = "Name must be entered")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Father name must be entered")
    @Size(min = 1, max = 50, message = "Father name must be between 1 and 50 characters")
    @Column(name = "father_name")
    private String fatherName;

    @NotBlank(message = "Class must be entered")
    @Column(name = "class_name")
    private String className;

    @NotBlank(message = "Section must be entered")
    @Column(name = "section")
    private String section;

    @NotNull(message = "Roll number must be entered")
    @Digits(integer = 100, fraction = 0, message = "Invalid roll number format")
    @Column(name = "roll_number")
    private Long rollNumber;

    @OneToMany(mappedBy = "studentRecord", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ResultEntity> results;

    @OneToMany(mappedBy = "studentRecord", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FeeCounterEntity> fee;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MasterEntity master;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TeacherRecordEntity teacherRecord;
}