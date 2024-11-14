package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.RegionInfoModel;
import com.example.SPA_APPS.service.AreaInfoService;
import com.example.SPA_APPS.service.RegionInfoService;
import com.example.SPA_APPS.service.TerritoryInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class RegionController {

    private final RegionInfoService regionInfoService;


    public RegionController(RegionInfoService regionInfoService) {
        this.regionInfoService = regionInfoService;
    }

    @PostMapping("/saveRegion")
    public ResponseEntity<BaseResponse> saveRegion(@RequestBody RegionInfoModel model) {
        BaseResponse baseResponse = regionInfoService.saveRegionInfo(model);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/updateRegion")
    public ResponseEntity<BaseResponse> updateRegion(@RequestBody RegionInfoModel model){
        BaseResponse baseResponse = regionInfoService.updateRegionInfo(model);
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/searchRegion")
    public ResponseEntity<BaseResponse> searchRegion(){
        BaseResponse baseResponse = regionInfoService.findAllRegionInfo();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAnyRegion")
    public ResponseEntity<BaseResponse> findAnyRegion(@RequestBody RegionInfoModel model){
        BaseResponse baseResponse = regionInfoService.findAnyInfo(model);
        return ResponseEntity.ok(baseResponse);
    }

}
