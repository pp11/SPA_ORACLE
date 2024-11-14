package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.PriorityProdRequest;
import com.example.SPA_APPS.service.PriorityProdService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class PriorityProdController {

    private final PriorityProdService priorityProdService;

    public PriorityProdController(PriorityProdService priorityProdService) {
        this.priorityProdService = priorityProdService;
    }


    @PostMapping("/savePriorityProd")
    public ResponseEntity<BaseResponse> savePriorityProd(@RequestBody PriorityProdRequest request) {
        try {
            // Call the service to save the master and detail records
            BaseResponse baseResponse = priorityProdService.savePriorityProd(request.getMstModel(), request.getDtlModels());

            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Error: " + e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @PostMapping("/updatePriorityProd")
    public ResponseEntity<BaseResponse> updatePriorityProd(@RequestBody PriorityProdRequest request) {
        try {
            // Call the service to save the master and detail records
            BaseResponse baseResponse = priorityProdService.updatePriorityProd(request.getMstModel(), request.getDtlModels());

            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Error: " + e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }

    @GetMapping("/searchPriorityProd")
    public ResponseEntity<BaseResponse> searchPriorityProd(@RequestParam Long id)
    {
        try {
            // Call the service to save the master and detail records
            BaseResponse baseResponse = priorityProdService.searchPrioProdByMstId(id);

            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Error: " + e.getMessage());
            return ResponseEntity.ok(errorResponse);
        }
    }




}
