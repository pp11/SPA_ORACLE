package com.example.SPA_APPS.serviceImpl;
import com.example.SPA_APPS.dto.MarketInfoDto;
import com.example.SPA_APPS.model.MarketInfoModel;
import com.example.SPA_APPS.repository.MarketInfoRepository;
import com.example.SPA_APPS.service.MarketInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketInfoServiceImpl implements MarketInfoService {
    private final MarketInfoRepository marketInfoRepository;

    private MarketInfoDto entityToDto(MarketInfoModel marketInfoModel) {
        MarketInfoDto marketInfoDto = new MarketInfoDto();
        BeanUtils.copyProperties(marketInfoModel, marketInfoDto);
        return marketInfoDto;
    }

    @Override
    public BaseResponse saveMarketInfo(MarketInfoModel marketInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            marketInfoModel.setCreateDate(LocalDateTime.now());
            marketInfoModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            MarketInfoModel savedModel = marketInfoRepository.saveOrUpdate(marketInfoModel);

            MarketInfoDto savedDto = new MarketInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setMarketInfoDto(savedDto);

            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateMarketInfo(MarketInfoModel marketInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            marketInfoModel.setUpdateDate(LocalDateTime.now());
            marketInfoModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            MarketInfoModel savedModel = marketInfoRepository.saveOrUpdate(marketInfoModel);

            MarketInfoDto savedDto = new MarketInfoDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setMarketInfoDto(savedDto);

            baseResponse.setMessage("Data updated successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAllMarketInfo() {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<MarketInfoModel> model=marketInfoRepository.findAll();
            baseResponse.setMarketInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse findAnyMarket(MarketInfoModel marketInfoModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<MarketInfoModel> model=marketInfoRepository.findByAnyData(marketInfoModel);
            baseResponse.setMarketInfoModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }
}
