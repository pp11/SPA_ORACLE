package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.RegionInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface RegionInfoService {
    public BaseResponse saveRegionInfo(RegionInfoModel regionInfoModel);
    public BaseResponse updateRegionInfo(RegionInfoModel regionInfoModel);

    public BaseResponse findAllRegionInfo();

    public BaseResponse findAnyInfo(RegionInfoModel regionInfoModel);
}
