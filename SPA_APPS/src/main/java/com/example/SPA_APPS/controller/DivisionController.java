package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.DivisionInfoModel;
import com.example.SPA_APPS.service.DivisionInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class DivisionController {
    private final DivisionInfoService divisionInfoService;

    public DivisionController(DivisionInfoService divisionInfoService) {
        this.divisionInfoService = divisionInfoService;
            }

    @PostMapping("/saveDivision")
    public ResponseEntity<BaseResponse> saveTarget(@RequestBody DivisionInfoModel divisionInfoModel) {
        BaseResponse baseResponse = divisionInfoService.saveDivisionInfo(divisionInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateDivision")
    public ResponseEntity<BaseResponse> updateTarget(@RequestBody DivisionInfoModel divisionInfoModel) {
        BaseResponse baseResponse = divisionInfoService.updateDivisionInfo(divisionInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/searchDivision")
    public  ResponseEntity<BaseResponse> findDivisionDetails(@RequestBody DivisionInfoModel divisionInfoModel){
        BaseResponse baseResponse=divisionInfoService.findByAnyInfo(divisionInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/deleteDivision")
    public  ResponseEntity<BaseResponse> deleteTarget(Long id){
        BaseResponse baseResponse=divisionInfoService.deleteDivision(id);
        return ResponseEntity.ok(baseResponse);
    }

}
