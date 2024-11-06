package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.DivisionInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface DivisionInfoService {

    public BaseResponse saveDivisionInfo(DivisionInfoModel divisionInfoModel);

    public BaseResponse updateDivisionInfo(DivisionInfoModel divisionInfoModel);

    public BaseResponse findByAnyInfo(DivisionInfoModel divisionInfoModel);

    public  BaseResponse deleteDivision(Long id);
}
