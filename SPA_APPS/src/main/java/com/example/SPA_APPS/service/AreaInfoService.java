package com.example.SPA_APPS.service;


import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

import java.util.List;

public interface AreaInfoService {
    public BaseResponse saveAreaInfo(AreaInfoModel areaInfoModel);
    public BaseResponse updateAreaInfo(AreaInfoModel areaInfoModel);

    public BaseResponse findAllAreaInfo();

    public BaseResponse findAnyInfo(AreaInfoModel areaInfoModel);
}
