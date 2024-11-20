package com.example.SPA_APPS.serviceImpl;


import com.example.SPA_APPS.dto.ProductInfoDto;
import com.example.SPA_APPS.dto.ProductPriceInfoDto;
import com.example.SPA_APPS.model.ProductInfoModel;
import com.example.SPA_APPS.model.ProductPriceInfoModel;
import com.example.SPA_APPS.repository.ProductInfoRepository;
import com.example.SPA_APPS.repository.ProductPriceInfoRepository;
import com.example.SPA_APPS.service.ProductPriceInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPriceInfoServiceImpl implements ProductPriceInfoService {

    private  final ProductPriceInfoRepository productPriceInfoRepository;
    private final ProductInfoRepository productInfoRepository;

    private ProductPriceInfoDto entityToDto(ProductPriceInfoModel productPriceInfoModel) {
        ProductPriceInfoDto productPriceInfoDto = new ProductPriceInfoDto();
        BeanUtils.copyProperties(productPriceInfoModel, productPriceInfoDto);
        return productPriceInfoDto;
    }

    @Override
    public BaseResponse saveProductPrice(ProductPriceInfoModel productPriceInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            productPriceInfoModel.setPriceDate(LocalDateTime.now().toLocalDate());
            productPriceInfoModel.setCreateDate(LocalDateTime.now());
            productPriceInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            try {
                ProductPriceInfoModel savedModel = productPriceInfoRepository.saveOrUpdate(productPriceInfoModel);
                ProductPriceInfoDto savedDto = new ProductPriceInfoDto();
                savedDto=entityToDto(savedModel);
                baseResponse.setProductPriceInfoDto(savedDto);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateProductPrice(ProductPriceInfoModel productPriceInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            productPriceInfoModel.setUpdateDate(LocalDateTime.now());
            productPriceInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            ProductPriceInfoModel savedModel = productPriceInfoRepository.saveOrUpdate(productPriceInfoModel);
            ProductPriceInfoDto savedDto = new ProductPriceInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setProductPriceInfoDto(savedDto);

            baseResponse.setMessage("Data updated successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAllProductPrice() {
        BaseResponse baseResponse=new BaseResponse();

            List<ProductPriceInfoModel> priceModelList=productPriceInfoRepository.findAllPrice();

            List<ProductInfoModel> productInfoModelList=productInfoRepository.findAll();

            Map<String, ProductInfoModel> productInfoMap = productInfoModelList.stream()
                    .collect(Collectors.toMap(ProductInfoModel::getProductCode, Function.identity()));

            priceModelList.forEach(priceInfo -> {
                ProductInfoModel productInfo = productInfoMap.get(priceInfo.getProductCode());
                if (productInfo != null) {
                    priceInfo.setProductName(productInfo.getProductName());
                    priceInfo.setPackSize(productInfo.getPackSize());
                }
            });
            baseResponse.setProductPriceInfoModelList(priceModelList);
            baseResponse.setMessage("success");

        return baseResponse;
    }

    @Override
    public BaseResponse findByAnyProductPrice(ProductPriceInfoModel productPriceInfoModel) {
        return null;
    }
}
