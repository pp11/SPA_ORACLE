package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.dto.CategoryInfoDto;

import com.example.SPA_APPS.model.CategoryInfoModel;
import com.example.SPA_APPS.repository.CategoryInfoRepository;
import com.example.SPA_APPS.service.CategoryInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryInfoServiceImpl implements CategoryInfoService {

    private final CategoryInfoRepository categoryInfoRepository;
    @Override
    public BaseResponse saveCategoryInfo(CategoryInfoModel categoryInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            categoryInfoModel.setCreateDate(LocalDateTime.now());
            categoryInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            CategoryInfoModel savedModel = categoryInfoRepository.saveOrUpdate(categoryInfoModel);

            CategoryInfoDto savedDto = new CategoryInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setCategoryInfoDto(savedDto);

            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    private CategoryInfoDto entityToDto(CategoryInfoModel categoryInfoModel) {
        CategoryInfoDto categoryInfoDto = new CategoryInfoDto();
        BeanUtils.copyProperties(categoryInfoModel, categoryInfoDto);
        return categoryInfoDto;
    }

    @Override
    public BaseResponse updateCategoryInfo(CategoryInfoModel categoryInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            categoryInfoModel.setUpdateDate(LocalDateTime.now());
            categoryInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            CategoryInfoModel savedModel = categoryInfoRepository.saveOrUpdate(categoryInfoModel);

            CategoryInfoDto savedDto = new CategoryInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setCategoryInfoDto(savedDto);

            baseResponse.setMessage("Data update successfully");

            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAnyCategory(CategoryInfoModel categoryInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<CategoryInfoModel> model=categoryInfoRepository.findByAnyData(categoryInfoModel);
            baseResponse.setCategoryInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse findAllCategory() {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<CategoryInfoModel> model=categoryInfoRepository.findAll();
            baseResponse.setCategoryInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }
}
