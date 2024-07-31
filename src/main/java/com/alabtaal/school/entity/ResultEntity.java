package com.alabtaal.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
@Entity
@Table(schema = "management", name = "results", uniqueConstraints = {
        @UniqueConstraint(name = "results_subject_student_uk", columnNames = {"subject", "studentRecord_id"})
})
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @NotNull(message = "Marks must be entered")
    @DecimalMin(value = "1", message = "Marks must be at least 1")
    @DecimalMax(value = "100", message = "Marks must be at most 100")
    @Column(name = "marks")
    private Double marks;

    @NotBlank(message = "Subject must be entered")
    @Size(min = 1, max = 50, message = "Subject must be between 1 and 50 characters")
    @Column(name = "subject")
    private String subject;

    @NotBlank(message = "Grade must be entered")
    @Size(min = 1, max = 10, message = "Grade must be between 1 and 10 characters")
    @Column(name = "grade")
    private String grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private StudentRecordEntity studentRecord;
}
