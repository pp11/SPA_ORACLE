package com.example.SPA_APPS.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
public class PriorityProdMstModel {
    private Long mstId;
    private Date effectStartDate;
    private Date effectEndDate;
    private String remarks;
    private List<PriorityProdDtlModel> details;
    private LocalDateTime createDate;
    private String createBy;
    private String createTerminal;
    private LocalDateTime updateDate;
    private String updateBy;
    private String updateTerminal;

    private List<PriorityProdDtlModel> dtlModels;
}
