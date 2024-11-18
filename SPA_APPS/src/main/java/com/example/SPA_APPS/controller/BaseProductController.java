package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.BaseProductModel;
import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.service.BaseProductService;
import com.example.SPA_APPS.service.BrandInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")

public class BaseProductController {
    private final BaseProductService baseProductService;

    public BaseProductController(BaseProductService baseProductService) {
        this.baseProductService = baseProductService;
    }

    @PostMapping("/saveBaseProd")
    public ResponseEntity<BaseResponse> saveBrand(@RequestBody BaseProductModel baseProductModel) {
        BaseResponse baseResponse = baseProductService.saveBaseProduct(baseProductModel);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateBaseProd")
    public ResponseEntity<BaseResponse> updateBrand(@RequestBody BaseProductModel baseProductModel){
        BaseResponse baseResponse = baseProductService.updateBaseProduct(baseProductModel);
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/findAnyBaseProd")
    public ResponseEntity<BaseResponse> findAnyBaseProd(@RequestBody BaseProductModel baseProductModel){
        BaseResponse baseResponse = baseProductService.findAnyBaseProduct(baseProductModel);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAllBaseProd")
    public ResponseEntity<BaseResponse> findAllBrand(){
        BaseResponse baseResponse = baseProductService.findAllBaseProduct();
        return ResponseEntity.ok(baseResponse);
    }
}
