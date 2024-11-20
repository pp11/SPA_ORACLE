package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.dto.AreaInfoDto;

import com.example.SPA_APPS.model.AreaInfoModel;

import com.example.SPA_APPS.repository.AreaInfoRepository;
import com.example.SPA_APPS.service.AreaInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AreaInfoServiceImpl implements AreaInfoService {
    @Autowired
    private AreaInfoRepository areaInfoRepository;
    private static AtomicInteger counter = new AtomicInteger(1);

    private AreaInfoDto entityToDto(AreaInfoModel areaInfoModel) {
        AreaInfoDto areaInfoDto = new AreaInfoDto();
        BeanUtils.copyProperties(areaInfoModel, areaInfoDto);
        return areaInfoDto;
    }

    @Override
    public BaseResponse saveAreaInfo(AreaInfoModel areaInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            areaInfoModel.setCreateDate(LocalDateTime.now());
            areaInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());

            AreaInfoModel savedModel = areaInfoRepository.saveOrUpdateAreaInfo(areaInfoModel);
            AreaInfoDto savedDto = new AreaInfoDto();
            savedDto=entityToDto(savedModel);

            baseResponse.setAreaInfoDto(savedDto);
            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving area info: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateAreaInfo(AreaInfoModel areaInfoModel) {
        BaseResponse baseResponse = new BaseResponse();

        try {
                areaInfoModel.setUpdateDate(LocalDateTime.now());
                areaInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
                areaInfoRepository.saveOrUpdateAreaInfo(areaInfoModel);
                AreaInfoDto savedDto = new AreaInfoDto();
                savedDto=entityToDto(areaInfoModel);
                baseResponse.setAreaInfoDto(savedDto);
                baseResponse.setMessage("Data updated successfully.");

        } catch (Exception e) {
            baseResponse.setMessage("Error updating area info: " + e.getMessage());
        }

        return baseResponse;
    }

    @Override
    public BaseResponse findAllAreaInfo() {
        List<AreaInfoModel> areaInfoList = areaInfoRepository.findAll();
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setAreaInfoModelList(areaInfoList);
        baseResponse.setMessage("success");
        return baseResponse;
    }



    @Override
    public BaseResponse findAnyInfo(AreaInfoModel areaInfoModel) {

        BaseResponse baseResponse = new BaseResponse();
        List<AreaInfoModel> areaInfoModels = areaInfoRepository.findByAnyData(areaInfoModel);
        baseResponse.setAreaInfoModelList(areaInfoModels);
        baseResponse.setMessage("success");
        return baseResponse;
    }

}
