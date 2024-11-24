package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RouteInfoModel {

    private String routeCode;
    private String routeName;
    private String routeDescription;
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
