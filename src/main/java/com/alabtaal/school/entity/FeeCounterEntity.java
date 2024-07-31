package com.alabtaal.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(schema = "management", name = "fee_counters")
public class FeeCounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id")
    private Long feeCounterId;

    @NotNull(message = "Amount must be entered")
    @Column(name = "amount")

    private Double amount;

    @NotNull(message = "Payment Date must be entered")
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @NotBlank(message = "Status must be entered")
    @Column(name = "status")
    @Size(min = 1, message = "Status must not be empty")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    private StudentRecordEntity studentRecord;

    @ManyToOne(fetch = FetchType.EAGER)
    private MasterEntity master;

}
