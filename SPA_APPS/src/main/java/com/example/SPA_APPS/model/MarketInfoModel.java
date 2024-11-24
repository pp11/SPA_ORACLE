package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketInfoModel {
    private Long id;
    private String marketCode;
    private String marketName;
    private String marketSegmentCode;
    private String marketAddress;
    private String remarks;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
