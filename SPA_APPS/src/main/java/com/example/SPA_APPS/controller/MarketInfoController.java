package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.MarketInfoModel;
import com.example.SPA_APPS.service.MarketInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class MarketInfoController {

    private  final  MarketInfoService marketInfoService;

    public MarketInfoController(MarketInfoService marketInfoService) {
        this.marketInfoService = marketInfoService;
    }

    @PostMapping("/saveMarket")
    public ResponseEntity<BaseResponse> saveMarket(@RequestBody MarketInfoModel marketInfoModel) {
        BaseResponse baseResponse = null;
        try {
            baseResponse = marketInfoService.saveMarketInfo(marketInfoModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateMarket")
    public ResponseEntity<BaseResponse> updateMarket(@RequestBody MarketInfoModel marketInfoModel){
        BaseResponse baseResponse = null;
        try {
            baseResponse = marketInfoService.updateMarketInfo(marketInfoModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAnyMarket")
    public ResponseEntity<BaseResponse> findAnyMarket(@RequestBody MarketInfoModel marketInfoModel){
        BaseResponse baseResponse = null;
        try {
            baseResponse = marketInfoService.findAnyMarket(marketInfoModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAllMarket")
    public ResponseEntity<BaseResponse> findAllMarket(){
        BaseResponse baseResponse = null;
        try {
            baseResponse = marketInfoService.findAllMarketInfo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(baseResponse);
    }
}
