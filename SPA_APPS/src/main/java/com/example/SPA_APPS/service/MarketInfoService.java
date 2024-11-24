package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.MarketInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface MarketInfoService {
    public BaseResponse saveMarketInfo(MarketInfoModel marketInfoModel);

    public BaseResponse updateMarketInfo(MarketInfoModel marketInfoModel);

    public BaseResponse findAllMarketInfo();

    public BaseResponse findAnyMarket(MarketInfoModel marketInfoModel);
}
