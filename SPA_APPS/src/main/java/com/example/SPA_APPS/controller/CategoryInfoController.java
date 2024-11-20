package com.example.SPA_APPS.controller;


import com.example.SPA_APPS.model.CategoryInfoModel;
import com.example.SPA_APPS.service.CategoryInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/master")
public class CategoryInfoController {

    private final CategoryInfoService categoryInfoService;

    public CategoryInfoController(CategoryInfoService categoryInfoService) {
        this.categoryInfoService = categoryInfoService;
    }

    @PostMapping("/saveCategory")
    public ResponseEntity<BaseResponse> saveCategory(@RequestBody CategoryInfoModel categoryInfoModel) {
        BaseResponse baseResponse = categoryInfoService.saveCategoryInfo(categoryInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateCategory")
    public ResponseEntity<BaseResponse> updateCategory(@RequestBody CategoryInfoModel categoryInfoModel){
        BaseResponse baseResponse = categoryInfoService.updateCategoryInfo(categoryInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAnyCategory")
    public ResponseEntity<BaseResponse> findAnyCategory(@RequestBody CategoryInfoModel categoryInfoModel){
        BaseResponse baseResponse = categoryInfoService.findAnyCategory(categoryInfoModel);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/findAllCategory")
    public ResponseEntity<BaseResponse> findAllBrand(){
        BaseResponse baseResponse = categoryInfoService.findAllCategory();
        return ResponseEntity.ok(baseResponse);
    }

}
