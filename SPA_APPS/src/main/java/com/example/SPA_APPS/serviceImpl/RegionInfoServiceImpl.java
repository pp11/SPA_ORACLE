package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.model.AreaInfoModel;
import com.example.SPA_APPS.model.RegionInfoModel;
import com.example.SPA_APPS.repository.AreaInfoRepository;
import com.example.SPA_APPS.repository.RegionInfoRepository;
import com.example.SPA_APPS.service.RegionInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RegionInfoServiceImpl implements RegionInfoService {
    @Autowired
    private RegionInfoRepository regionInfoRepository;

    @Override
    public BaseResponse saveRegionInfo(RegionInfoModel regionInfoModel) {
        BaseResponse baseResponse=new BaseResponse();


        try {
            regionInfoModel.setCreateDate(LocalDateTime.now());
            regionInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            RegionInfoModel savedModel = regionInfoRepository.saveOrUpdate(regionInfoModel);
            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateRegionInfo(RegionInfoModel regionInfoModel) {
        BaseResponse baseResponse = new BaseResponse();

        try {
            regionInfoModel.setUpdateDate(LocalDateTime.now());
            regionInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            regionInfoRepository.saveOrUpdate(regionInfoModel);
            baseResponse.setMessage("Data updated successfully.");

        } catch (Exception e) {
            baseResponse.setMessage("Error updating data: " + e.getMessage());
        }

        return baseResponse;
    }

    @Override
    public BaseResponse findAllRegionInfo() {
        List<RegionInfoModel> regionInfoModels = regionInfoRepository.findAll();
        BaseResponse baseResponse=new BaseResponse();

        baseResponse.setRegionInfoModelList(regionInfoModels);
        baseResponse.setMessage("success");
        return baseResponse;
    }

    @Override
    public BaseResponse findAnyInfo(RegionInfoModel regionInfoModel) {

        BaseResponse baseResponse=new BaseResponse();

        List<RegionInfoModel> modelList=regionInfoRepository.findByAnyData(regionInfoModel);
        baseResponse.setRegionInfoModelList(modelList);
        baseResponse.setMessage("success");

        return  baseResponse;
    }
}
