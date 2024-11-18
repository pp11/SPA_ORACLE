package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.BaseProductModel;
import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface BaseProductService {
    public BaseResponse saveBaseProduct(BaseProductModel baseProductModel);
    public BaseResponse updateBaseProduct(BaseProductModel baseProductModel);

    public BaseResponse findAnyBaseProduct(BaseProductModel baseProductModel);

    public BaseResponse findAllBaseProduct();
}
