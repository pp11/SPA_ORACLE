package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.service.BrandInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class BrandInfoController {

    private final BrandInfoService brandInfoService;

    public BrandInfoController(BrandInfoService brandInfoService) {
        this.brandInfoService = brandInfoService;
    }

    @PostMapping("/saveBrand")
    public ResponseEntity<BaseResponse> saveBrand(@RequestBody BrandInfoModel brandInfoModel) {
        BaseResponse baseResponse = brandInfoService.saveBrandInfo(brandInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateBrand")
    public ResponseEntity<BaseResponse> updateBrand(@RequestBody BrandInfoModel brandInfoModel){
        BaseResponse baseResponse = brandInfoService.updateBrandInfo(brandInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAnyBrand")
    public ResponseEntity<BaseResponse> findAnyBrand(@RequestBody BrandInfoModel brandInfoModel){
        BaseResponse baseResponse = brandInfoService.findAnyBrand(brandInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAllBrand")
    public ResponseEntity<BaseResponse> findAllBrand(){
        BaseResponse baseResponse = brandInfoService.findAllBrand();
        return ResponseEntity.ok(baseResponse);
    }

}
