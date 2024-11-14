package com.example.SPA_APPS.utils;

import com.example.SPA_APPS.dto.AreaInfoDto;
import com.example.SPA_APPS.dto.DivisionInfoDto;
import com.example.SPA_APPS.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    private DivisionInfoDto divisionInfoDto;
    private List<DivisionInfoDto> divisionInfoDtos;
    private String message;

    private List<AreaInfoModel> areaInfoModelList;

    private  List<AreaInfoDto> areaInfoDtos;

    private List<RegionInfoModel> regionInfoModelList;

    private PriorityProdMstModel priorityProdMstModel;

    private List<TerritoryInfoModel> territoryInfoModelList;

    private List<ProductInfoModel> productInfoModelList;

    private List<BrandInfoModel> brandInfoModelList;



}
