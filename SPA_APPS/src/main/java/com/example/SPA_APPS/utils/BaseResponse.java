package com.example.SPA_APPS.utils;

import com.example.SPA_APPS.dto.*;
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

    private AreaInfoDto areaInfoDto;
    private List<AreaInfoModel> areaInfoModelList;

    private BrandInfoDto brandInfoDto;
    private List<BrandInfoModel> brandInfoModelList;

    private BaseProductInfoDto baseProductInfoDto;
    private List<BaseProductModel> baseProductModelList;

    private CategoryInfoDto categoryInfoDto;
    private List<CategoryInfoModel> categoryInfoModelList;

    private ProductInfoDto productInfoDto;
    private List<ProductInfoModel> productInfoModelList;

    private ProductPriceInfoDto productPriceInfoDto;
    private List<ProductPriceInfoModel> productPriceInfoModelList;

    private MarketSegmentDto marketSegmentDto;
    private List<MarketSegmentModel> marketSegmentModelList;

    private List<RegionInfoModel> regionInfoModelList;

    private PriorityProdMstModel priorityProdMstModel;

    private List<TerritoryInfoModel> territoryInfoModelList;










}
