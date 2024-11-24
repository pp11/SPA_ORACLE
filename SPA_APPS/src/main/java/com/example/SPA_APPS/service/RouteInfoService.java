package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.RouteInfoModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface RouteInfoService {
    public BaseResponse saveRouteInfo(RouteInfoModel routeInfoModel);

    public BaseResponse updateRouteInfo(RouteInfoModel routeInfoModel);

    public BaseResponse findAllRouteInfo();

    public BaseResponse findAnyRoute(RouteInfoModel routeInfoModel);
}
