package com.example.SPA_APPS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetDto {

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
