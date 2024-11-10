package com.example.SPA_APPS.utils;

import com.example.SPA_APPS.dto.AreaInfoDto;
import com.example.SPA_APPS.dto.DivisionInfoDto;
import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.RegionInfoModel;
import lombok.Data;

import java.util.List;

@Data
public class BaseResponse {
    private DivisionInfoDto divisionInfoDto;
    private List<DivisionInfoDto> divisionInfoDtos;
    private String message;

    private List<AreaInfoModel> areaInfoModelList;
    private  List<AreaInfoDto> areaInfoDtos;

    private List<RegionInfoModel> regionInfoModelList;



}
