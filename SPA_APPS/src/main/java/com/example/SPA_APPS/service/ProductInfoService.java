package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.ProductInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface ProductInfoService {

    public BaseResponse saveProduct(ProductInfoModel productInfoModel);

    public BaseResponse updateProduct(ProductInfoModel productInfoModel);
    public BaseResponse findAllProduct();
    public BaseResponse findByAnyProduct(ProductInfoModel productInfoModel);
}
