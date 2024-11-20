package com.example.SPA_APPS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryInfoDto {
    private String categoryCode;
    private String categoryName;
    private String brandCode;
    private String brandName;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
