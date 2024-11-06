package com.example.SPA_APPS.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "division_info", uniqueConstraints = @UniqueConstraint(columnNames = {"divisionCode"}))
@Data
public class DivisionInfo {
    @Id
    @SequenceGenerator(
            name = "division_sequence",
            sequenceName = "division_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "division_sequence"
    )
    @Column(updatable = false)
    private Long id;

    private String divisionCode;
    private String divisionName;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate;
}
