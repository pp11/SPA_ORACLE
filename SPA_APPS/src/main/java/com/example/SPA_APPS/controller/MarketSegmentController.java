package com.example.SPA_APPS.controller;


import com.example.SPA_APPS.model.CategoryInfoModel;
import com.example.SPA_APPS.model.MarketSegmentModel;
import com.example.SPA_APPS.service.MarketSegmentService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class MarketSegmentController {

    private final MarketSegmentService marketSegmentService;

    public MarketSegmentController(MarketSegmentService marketSegmentService) {
        this.marketSegmentService = marketSegmentService;
    }

    @PostMapping("/saveMarketSegment")
    public ResponseEntity<BaseResponse> saveMarketSegment(@RequestBody MarketSegmentModel marketSegmentModel) {
        BaseResponse baseResponse = marketSegmentService.saveMarketSegment(marketSegmentModel);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateMarketSegment")
    public ResponseEntity<BaseResponse> updateMarketSegment(@RequestBody MarketSegmentModel marketSegmentModel){
        BaseResponse baseResponse = marketSegmentService.updateMarketSegment(marketSegmentModel);
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/findAnyMarketSegment")
    public ResponseEntity<BaseResponse> findAnyMarketSegment(@RequestBody MarketSegmentModel marketSegmentModel){
        BaseResponse baseResponse = marketSegmentService.findAnyMarketSegment(marketSegmentModel);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAllMarketSegment")
    public ResponseEntity<BaseResponse> findAllMarketSegment(){
        BaseResponse baseResponse = marketSegmentService.findAllMarketSegment();
        return ResponseEntity.ok(baseResponse);
    }
}
