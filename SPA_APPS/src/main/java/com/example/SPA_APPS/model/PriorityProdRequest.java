package com.example.SPA_APPS.model;

import lombok.Data;

import java.util.List;
@Data
public class PriorityProdRequest {
    private PriorityProdMstModel mstModel;
    private List<PriorityProdDtlModel> dtlModels;
}
