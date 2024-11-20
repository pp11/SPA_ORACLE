package com.example.SPA_APPS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseProductInfoDto {
    private String baseProductCode;
    private String baseProductName;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
