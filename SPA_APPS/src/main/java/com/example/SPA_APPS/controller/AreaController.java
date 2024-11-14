package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.DivisionInfoModel;
import com.example.SPA_APPS.service.AreaInfoService;
import com.example.SPA_APPS.service.DivisionInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class AreaController {
    private final AreaInfoService areaInfoService;

    public AreaController(AreaInfoService areaInfoService) {
        this.areaInfoService = areaInfoService;
    }

    @PostMapping("/saveArea")
    public ResponseEntity<BaseResponse> saveArea(@RequestBody AreaInfoModel areaInfoModel) {
        BaseResponse baseResponse = areaInfoService.saveAreaInfo(areaInfoModel);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/updateArea")
    public ResponseEntity<BaseResponse> updateArea(@RequestBody AreaInfoModel areaInfoModel){
        BaseResponse baseResponse = areaInfoService.updateAreaInfo(areaInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/searchArea")
    public ResponseEntity<BaseResponse> searchArea(){
        BaseResponse baseResponse = areaInfoService.findAllAreaInfo();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAnyArea")
    public ResponseEntity<BaseResponse> findAnyArea(@RequestBody AreaInfoModel areaInfoModel){
        BaseResponse baseResponse = areaInfoService.findAnyInfo(areaInfoModel);
        return ResponseEntity.ok(baseResponse);
    }
}
