package com.example.SPA_APPS.service;
import com.example.SPA_APPS.model.MarketSegmentModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface MarketSegmentService {

    public BaseResponse saveMarketSegment(MarketSegmentModel marketSegmentModel);
    public BaseResponse updateMarketSegment(MarketSegmentModel marketSegmentModel);

    public BaseResponse findAnyMarketSegment(MarketSegmentModel marketSegmentModel);

    public BaseResponse findAllMarketSegment();
}
