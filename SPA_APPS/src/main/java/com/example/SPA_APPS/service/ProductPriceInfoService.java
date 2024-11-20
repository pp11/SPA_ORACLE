package com.example.SPA_APPS.service;


import com.example.SPA_APPS.model.ProductPriceInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface ProductPriceInfoService {

    public BaseResponse saveProductPrice(ProductPriceInfoModel productPriceInfoModel);

    public BaseResponse updateProductPrice(ProductPriceInfoModel productPriceInfoModel);
    public BaseResponse findAllProductPrice();
    public BaseResponse findByAnyProductPrice(ProductPriceInfoModel productPriceInfoModel);
}
