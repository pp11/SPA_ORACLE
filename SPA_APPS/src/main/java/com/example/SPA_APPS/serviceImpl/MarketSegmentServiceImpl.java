package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.dto.MarketSegmentDto;
import com.example.SPA_APPS.model.CategoryInfoModel;
import com.example.SPA_APPS.model.MarketSegmentModel;
import com.example.SPA_APPS.repository.MarketSegmentRepository;
import com.example.SPA_APPS.service.MarketSegmentService;
import com.example.SPA_APPS.utils.BaseResponse;
import com.example.SPA_APPS.utils.IpAddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketSegmentServiceImpl implements MarketSegmentService {

    private final MarketSegmentRepository marketSegmentRepository;

    private MarketSegmentDto entityToDto(MarketSegmentModel marketSegmentModel) {
        MarketSegmentDto marketSegmentDto = new MarketSegmentDto();
        BeanUtils.copyProperties(marketSegmentModel, marketSegmentDto);
        return marketSegmentDto;
    }

    @Override
    public BaseResponse saveMarketSegment(MarketSegmentModel marketSegmentModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            marketSegmentModel.setCreateDate(LocalDateTime.now());
            marketSegmentModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
            MarketSegmentModel savedModel = marketSegmentRepository.saveOrUpdate(marketSegmentModel);

            MarketSegmentDto savedDto = new MarketSegmentDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setMarketSegmentDto(savedDto);

            baseResponse.setMessage("Data saved successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse updateMarketSegment(MarketSegmentModel marketSegmentModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            marketSegmentModel.setUpdateDate(LocalDateTime.now());
            marketSegmentModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());
            MarketSegmentModel savedModel = marketSegmentRepository.saveOrUpdate(marketSegmentModel);

            MarketSegmentDto savedDto = new MarketSegmentDto();
            savedDto=entityToDto(savedModel);
            baseResponse.setMarketSegmentDto(savedDto);

            baseResponse.setMessage("Data updated successfully");
            return  baseResponse;
        } catch (Exception e) {
            baseResponse.setMessage("Error saving data: " + e.getMessage());
            return baseResponse;
        }
    }

    @Override
    public BaseResponse findAnyMarketSegment(MarketSegmentModel marketSegmentModel) {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<MarketSegmentModel> model=marketSegmentRepository.findByAnyData(marketSegmentModel);
            baseResponse.setMarketSegmentModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }

    @Override
    public BaseResponse findAllMarketSegment() {
        BaseResponse baseResponse=new BaseResponse();
        try {
            List<MarketSegmentModel> model=marketSegmentRepository.findAll();
            baseResponse.setMarketSegmentModelList(model);
            baseResponse.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return baseResponse;
    }
}
