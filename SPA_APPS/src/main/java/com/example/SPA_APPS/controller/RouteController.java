package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.RouteInfoModel;
import com.example.SPA_APPS.service.RouteInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spa/api/v1/master")
public class RouteController {
  private  final RouteInfoService routeInfoService;

    public RouteController(RouteInfoService routeInfoService) {
        this.routeInfoService = routeInfoService;
    }

    @PostMapping("/saveRoute")
    public ResponseEntity<BaseResponse> saveRoute(@RequestBody RouteInfoModel routeInfoModel) {
        BaseResponse baseResponse = null;
        try {
            baseResponse = routeInfoService.saveRouteInfo(routeInfoModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateRoute")
    public ResponseEntity<BaseResponse> updateRoute(@RequestBody RouteInfoModel routeInfoModel){
        BaseResponse baseResponse = null;
        try {
            baseResponse = routeInfoService.updateRouteInfo(routeInfoModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(baseResponse);
    }
}
