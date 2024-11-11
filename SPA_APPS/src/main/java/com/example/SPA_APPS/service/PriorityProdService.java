package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.PriorityProdDtlModel;
import com.example.SPA_APPS.model.PriorityProdMstModel;
import com.example.SPA_APPS.utils.BaseResponse;

import java.util.List;

public interface PriorityProdService {
    public BaseResponse savePriorityProd(PriorityProdMstModel mstModel, List<PriorityProdDtlModel> dtlModels);

    public BaseResponse searchPrioProdByMstId(Long id);

    public BaseResponse updatePriorityProd(PriorityProdMstModel mstModel, List<PriorityProdDtlModel> dtlModels);

}
