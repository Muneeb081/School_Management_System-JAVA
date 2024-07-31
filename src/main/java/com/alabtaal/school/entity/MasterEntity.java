package com.alabtaal.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(schema = "management", name = "master_table", uniqueConstraints = {
        @UniqueConstraint(name = "master_email_uk", columnNames = "email")
})
public class MasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Email must be entered")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Phone number must be entered")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "Address must be entered")
    @Column(name = "address")
    private String address;

    @OneToOne(mappedBy = "master")
    private StudentRecordEntity student;

    @OneToOne(mappedBy = "master")
    private TeacherRecordEntity teacher;

    @OneToMany(mappedBy = "master")
    private List<FeeCounterEntity> fees;

}
