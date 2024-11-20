package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.model.CategoryInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface CategoryInfoService {

    public BaseResponse saveCategoryInfo(CategoryInfoModel categoryInfoModel);
    public BaseResponse updateCategoryInfo(CategoryInfoModel categoryInfoModel);

    public BaseResponse findAnyCategory(CategoryInfoModel categoryInfoModel);

    public BaseResponse findAllCategory();
}
