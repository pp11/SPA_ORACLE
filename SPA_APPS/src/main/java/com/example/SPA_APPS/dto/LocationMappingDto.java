package com.example.SPA_APPS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationMappingDto {
    private Long id;
    private Long divisionId;
    private String divisionCode;
    private String divisionName;
    private Long regionId;
    private String regionCode;
    private String regionName;
    private Long areaId;
    private String areaCode;
    private String areaName;
    private Long territoryId;
    private String territoryCode;
    private String territoryName;
    private Long marketId;
    private String marketCode;
    private String marketName;
    private LocalDateTime effectStartDate;
    private LocalDateTime effectEndDate;
    private String createBy;
    private String createTerminal;
    private String updateBy;
    private String updateTerminal;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
