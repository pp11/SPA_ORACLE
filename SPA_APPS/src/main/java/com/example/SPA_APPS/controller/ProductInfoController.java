package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.ProductInfoModel;
import com.example.SPA_APPS.model.TerritoryInfoModel;
import com.example.SPA_APPS.service.ProductInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class ProductInfoController {

    private final ProductInfoService productInfoService;


    public ProductInfoController(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }


    @PostMapping("/saveProduct")
    public ResponseEntity<BaseResponse> saveProduct(@RequestBody ProductInfoModel model) {
        BaseResponse baseResponse = productInfoService.saveProduct(model);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<BaseResponse> updateProduct(@RequestBody ProductInfoModel model) {
        BaseResponse baseResponse = productInfoService.updateProduct(model);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/searchProduct")
    public ResponseEntity<BaseResponse> searchProduct() {
        BaseResponse baseResponse = productInfoService.findAllProduct();
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/findAnyProduct")
    public ResponseEntity<BaseResponse> findAnyProduct(@RequestBody ProductInfoModel model) {
        BaseResponse baseResponse = productInfoService.findByAnyProduct(model);
        return ResponseEntity.ok(baseResponse);
    }
}
