package com.example.SPA_APPS.serviceImpl;



import com.example.SPA_APPS.dto.ProductInfoDto;
import com.example.SPA_APPS.model.ProductInfoModel;
import com.example.SPA_APPS.repository.BrandRepository;
import com.example.SPA_APPS.repository.ProductInfoRepository;
import com.example.SPA_APPS.service.ProductInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoRepository productInfoRepository;
    private final BrandRepository brandRepository;

    private ProductInfoDto entityToDto(ProductInfoModel productInfoModel) {
        ProductInfoDto productInfoDto = new ProductInfoDto();
        BeanUtils.copyProperties(productInfoModel, productInfoDto);
        return productInfoDto;
    }


    @Override
    public BaseResponse saveProduct(ProductInfoModel productInfoModel) {

        BaseResponse baseResponse=new BaseResponse();
        try {
            productInfoModel.setCreateDate(LocalDateTime.now());
            productInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            ProductInfoModel savedModel = productInfoRepository.saveOrUpdate(productInfoModel);

            ProductInfoDto savedDto = new ProductInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setProductInfoDto(savedDto);

            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateProduct(ProductInfoModel productInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            productInfoModel.setUpdateDate(LocalDateTime.now());
            productInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            ProductInfoModel savedModel = productInfoRepository.saveOrUpdate(productInfoModel);

            ProductInfoDto savedDto = new ProductInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setProductInfoDto(savedDto);

            baseResponse.setMessage("Data updated successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAllProduct() {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<ProductInfoModel> model=productInfoRepository.findAll();
            baseResponse.setProductInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse findByAnyProduct(ProductInfoModel productInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<ProductInfoModel> model=productInfoRepository.findByAnyData(productInfoModel);
            baseResponse.setProductInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }
}
