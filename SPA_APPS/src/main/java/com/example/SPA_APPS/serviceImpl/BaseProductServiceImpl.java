package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.model.BaseProductModel;
import com.example.SPA_APPS.model.BrandInfoModel;
import com.example.SPA_APPS.repository.BaseProductRepository;
import com.example.SPA_APPS.service.BaseProductService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseProductServiceImpl implements BaseProductService {
    private final BaseProductRepository baseProductRepository;

    @Override
    public BaseResponse saveBaseProduct(BaseProductModel baseProductModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            baseProductModel.setCreateDate(LocalDateTime.now());
            baseProductModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            BaseProductModel savedModel = baseProductRepository.saveOrUpdate(baseProductModel);
            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateBaseProduct(BaseProductModel baseProductModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            baseProductModel.setUpdateDate(LocalDateTime.now());
            baseProductModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            BaseProductModel savedModel = baseProductRepository.saveOrUpdate(baseProductModel);
            baseResponse.setMessage("Data updated successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAnyBaseProduct(BaseProductModel baseProductModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<BaseProductModel> model=baseProductRepository.findByAnyData(baseProductModel);
            baseResponse.setBaseProductModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse findAllBaseProduct() {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<BaseProductModel> model=baseProductRepository.findAllBaseProduct();
            baseResponse.setBaseProductModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }
}
