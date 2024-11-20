package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketSegmentModel {
    private String marketSegmentCode;
    private String marketSegmentName;
    private String status;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
