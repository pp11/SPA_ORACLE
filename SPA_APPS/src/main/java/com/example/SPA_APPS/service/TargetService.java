package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.TargetModel;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TargetService {
    public BaseResponse saveTarget(TargetModel targetModel);

    public BaseResponse updateTarget(TargetModel targetModel);


    public  BaseResponse findTargetDetails(String year, String month);

    public BaseResponse findByAnyInfo(TargetModel targetModel);

    public BaseResponse deleteTarget(Long id);

    //public List<MarketModel> getMarket(String marketCode);

    public BaseResponse saveFromExcel(MultipartFile file) throws IOException;


}
