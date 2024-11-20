package com.example.SPA_APPS.serviceImpl;


import com.example.SPA_APPS.dto.BrandInfoDto;
import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.repository.BrandRepository;
import com.example.SPA_APPS.service.BrandInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandInfoServiceImpl implements BrandInfoService {

    private final BrandRepository brandRepository;
    private BrandInfoDto entityToDto(BrandInfoModel brandInfoModel) {
        BrandInfoDto brandInfoDto = new BrandInfoDto();
        BeanUtils.copyProperties(brandInfoModel, brandInfoDto);
        return brandInfoDto;
    }


    @Override
    public BaseResponse saveBrandInfo(BrandInfoModel brandInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            brandInfoModel.setCreateDate(LocalDateTime.now());
            brandInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            BrandInfoModel savedModel = brandRepository.saveOrUpdate(brandInfoModel);

            BrandInfoDto savedDto = new BrandInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setBrandInfoDto(savedDto);

            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateBrandInfo(BrandInfoModel brandInfoModel) {

        BaseResponse baseResponse=new BaseResponse();
        try {
            brandInfoModel.setUpdateDate(LocalDateTime.now());
            brandInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            BrandInfoModel savedModel = brandRepository.saveOrUpdate(brandInfoModel);

            BrandInfoDto savedDto = new BrandInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setBrandInfoDto(savedDto);

            baseResponse.setMessage("Data update successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAnyBrand(BrandInfoModel brandInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<BrandInfoModel> model=brandRepository.findByAnyData(brandInfoModel);

            baseResponse.setBrandInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse findAllBrand() {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<BrandInfoModel> model=brandRepository.findAll();
            baseResponse.setBrandInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }


}
