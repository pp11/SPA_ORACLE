package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.ProductInfoModel;
import com.example.SPA_APPS.model.ProductPriceInfoModel;
import com.example.SPA_APPS.service.ProductInfoService;
import com.example.SPA_APPS.service.ProductPriceInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class ProductPriceInfoController {

    private final ProductPriceInfoService productPriceInfoService;

    public ProductPriceInfoController(ProductPriceInfoService productPriceInfoService) {
        this.productPriceInfoService = productPriceInfoService;
    }

    @PostMapping("/saveProductPrice")
    public ResponseEntity<BaseResponse> saveProduct(@RequestBody ProductPriceInfoModel model) {
        BaseResponse baseResponse = null;
        try {
            baseResponse = productPriceInfoService.saveProductPrice(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateProductPrice")
    public ResponseEntity<BaseResponse> updateProductPrice(@RequestBody ProductPriceInfoModel model) {
        BaseResponse baseResponse = productPriceInfoService.updateProductPrice(model);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAllPrice")
    public ResponseEntity<BaseResponse> searchProductPrice() {
        BaseResponse baseResponse = productPriceInfoService.findAllProductPrice();
        return ResponseEntity.ok(baseResponse);
    }
}
