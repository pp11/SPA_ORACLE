package com.example.SPA_APPS.serviceImpl;
import com.example.SPA_APPS.dto.RouteInfoDto;
import com.example.SPA_APPS.model.RouteInfoModel;

import com.example.SPA_APPS.repository.RouteInfoRepository;
import com.example.SPA_APPS.service.RouteInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RouteInfoServiceImpl implements RouteInfoService {

    private final RouteInfoRepository routeInfoRepository;

    private RouteInfoDto entityToDto(RouteInfoModel routeInfoModel) {
        RouteInfoDto routeInfoDto = new RouteInfoDto();
        BeanUtils.copyProperties(routeInfoModel, routeInfoDto);
        return routeInfoDto;
    }

    @Override
    public BaseResponse saveRouteInfo(RouteInfoModel routeInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            routeInfoModel.setCreateDate(LocalDateTime.now());
            routeInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            RouteInfoModel savedModel = routeInfoRepository.saveOrUpdate(routeInfoModel);

            RouteInfoDto savedDto = new RouteInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setRouteInfoDto(savedDto);
            baseResponse.setMessage("Data saved successfully");

            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateRouteInfo(RouteInfoModel routeInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            routeInfoModel.setUpdateDate(LocalDateTime.now());
            routeInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            RouteInfoModel savedModel = routeInfoRepository.saveOrUpdate(routeInfoModel);

            RouteInfoDto savedDto = new RouteInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setRouteInfoDto(savedDto);
            baseResponse.setMessage("Data updated successfully");
            return  baseResponse;

        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAllRouteInfo() {
        return null;
    }

    @Override
    public BaseResponse findAnyRoute(RouteInfoModel routeInfoModel) {
        return null;
    }
}
