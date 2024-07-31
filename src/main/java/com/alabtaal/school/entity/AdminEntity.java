package com.alabtaal.school.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(schema = "management", name = "admins", uniqueConstraints = {
        @UniqueConstraint(name = "Admins_uk1", columnNames = "name")
})

public class AdminEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column( name = "admin_id")
    private Long adminId;
    @NotBlank(message = "Name must be entered")
    @Column( name = "name")
    private String name;
    @NotBlank (message = "Password must be entered")
    @Column (name = "password")
    private String password;

}
