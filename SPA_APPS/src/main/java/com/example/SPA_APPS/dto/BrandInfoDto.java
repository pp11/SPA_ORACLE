package com.example.SPA_APPS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandInfoDto {
    private String brandCode;
    private String brandName;
    private String brandType;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
