package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface BrandInfoService {

    public BaseResponse saveBrandInfo(BrandInfoModel brandInfoModel);
    public BaseResponse updateBrandInfo(BrandInfoModel brandInfoModel);

    public BaseResponse findAnyBrand(BrandInfoModel brandInfoModel);

    public BaseResponse findAllBrand();

}
