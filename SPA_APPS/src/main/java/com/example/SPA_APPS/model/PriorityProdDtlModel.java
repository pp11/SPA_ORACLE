package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PriorityProdDtlModel {
    private Long dtlId;
    private Long mstId;
    private String productCode;
    private String status;
    private String remarks;
    private LocalDateTime createDate;
    private String createBy;
    private String createTerminal;
    private LocalDateTime updateDate;
    private String updateBy;
    private String updateTerminal;
}
