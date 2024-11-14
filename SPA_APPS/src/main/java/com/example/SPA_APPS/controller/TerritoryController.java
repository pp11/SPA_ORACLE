package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.RegionInfoModel;
import com.example.SPA_APPS.model.TerritoryInfoModel;
import com.example.SPA_APPS.service.RegionInfoService;
import com.example.SPA_APPS.service.TerritoryInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class TerritoryController {

    private final TerritoryInfoService territoryInfoService;

    public TerritoryController(TerritoryInfoService territoryInfoService) {
        this.territoryInfoService = territoryInfoService;
    }


    @PostMapping("/saveTerritory")
    public ResponseEntity<BaseResponse> saveTerritory(@RequestBody TerritoryInfoModel model) {
        BaseResponse baseResponse = territoryInfoService.saveTerritoryInfo(model);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateTerritory")
    public ResponseEntity<BaseResponse> updateTerritory(@RequestBody TerritoryInfoModel model) {
        BaseResponse baseResponse = territoryInfoService.updateTerritoryinfo(model);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/searchTerritory")
    public ResponseEntity<BaseResponse> searchRegion(){
        BaseResponse baseResponse = territoryInfoService.findAllTerritoryInfo();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAnyTerritory")
    public ResponseEntity<BaseResponse> findAnyRegion(@RequestBody TerritoryInfoModel model){
        BaseResponse baseResponse = territoryInfoService.findAnyInfo(model);
        return ResponseEntity.ok(baseResponse);
    }
}
