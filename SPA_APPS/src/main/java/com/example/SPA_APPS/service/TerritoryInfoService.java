package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.RegionInfoModel;
import com.example.SPA_APPS.model.TerritoryInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface TerritoryInfoService {

    public BaseResponse saveTerritoryInfo(TerritoryInfoModel territoryInfoModel);

    public BaseResponse updateTerritoryinfo(TerritoryInfoModel territoryInfoModel);

    public BaseResponse findAllTerritoryInfo();

    public BaseResponse findAnyInfo(TerritoryInfoModel territoryInfoModel);


}
