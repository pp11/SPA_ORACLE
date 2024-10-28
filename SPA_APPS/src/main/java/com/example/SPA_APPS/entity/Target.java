package com.example.SPA_APPS.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Target", uniqueConstraints = @UniqueConstraint(columnNames = {"marketCode", "productCode", "year", "month"}))
@Data
public class Target {
    @Id
    @SequenceGenerator(
            name = "target_sequence",
            sequenceName = "target_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "target_sequence"
    )
    @Column(updatable = false)
    private Long id;
    private String year;
    private String month;
    private String marketCode;
    private String marketName;
    private String productCode;
    private String productName;
    private String packSize;
    private Double unitTp;
    private Double targetQty;
    private Double targetValue;

    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate;
}
