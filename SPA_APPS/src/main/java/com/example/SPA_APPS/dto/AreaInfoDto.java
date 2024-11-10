package com.example.SPA_APPS.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AreaInfoDto {
    private Long id;

    private String areaCode;
    private String areaName;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
