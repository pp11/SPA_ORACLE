package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.model.RegionInfoModel;
import com.example.SPA_APPS.model.TerritoryInfoModel;
import com.example.SPA_APPS.repository.RegionInfoRepository;
import com.example.SPA_APPS.repository.TerritoryInfoRepository;
import com.example.SPA_APPS.service.TerritoryInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TerritoryInfoServiceImpl implements TerritoryInfoService {

    @Autowired
    private TerritoryInfoRepository territoryInfoRepository;

    @Override
    public BaseResponse saveTerritoryInfo(TerritoryInfoModel territoryInfoModel) {

        BaseResponse baseResponse=new BaseResponse();
        try {
            territoryInfoModel.setCreateDate(LocalDateTime.now());
            territoryInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            TerritoryInfoModel savedModel = territoryInfoRepository.saveOrUpdate(territoryInfoModel);
            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateTerritoryinfo(TerritoryInfoModel territoryInfoModel) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            territoryInfoModel.setUpdateDate(LocalDateTime.now());
            territoryInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            TerritoryInfoModel savedModel = territoryInfoRepository.saveOrUpdate(territoryInfoModel);
            baseResponse.setMessage("Data updated successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }

    }

    @Override
    public BaseResponse findAllTerritoryInfo() {
        List<TerritoryInfoModel> territoryInfoModels = territoryInfoRepository.findAll();
        BaseResponse baseResponse=new BaseResponse();

        baseResponse.setTerritoryInfoModelList(territoryInfoModels);
        baseResponse.setMessage("success");
        return baseResponse;
    }

    @Override
    public BaseResponse findAnyInfo(TerritoryInfoModel territoryInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        List<TerritoryInfoModel> territoryInfoModels = territoryInfoRepository.findByAnyData(territoryInfoModel);

        baseResponse.setTerritoryInfoModelList(territoryInfoModels);
        baseResponse.setMessage("success");
        return baseResponse;

    }


}
