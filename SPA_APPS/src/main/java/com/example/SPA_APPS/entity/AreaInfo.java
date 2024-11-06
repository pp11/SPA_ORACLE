package com.example.SPA_APPS.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "area_info", uniqueConstraints = @UniqueConstraint(columnNames = {"areaCode"}))
@Data
public class AreaInfo {
    @Id
    @SequenceGenerator(
            name = "area_sequence",
            sequenceName = "area_sequence",
            initialValue = 1,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "area_sequence"
    )
    @Column(updatable = false)
    private Long id;

    private String areaCode;
    private String areaName;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate;
}
