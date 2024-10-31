package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TargetModel {
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

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}

