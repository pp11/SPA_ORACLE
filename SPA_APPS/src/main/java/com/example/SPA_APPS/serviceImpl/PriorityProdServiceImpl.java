package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.model.PriorityProdDtlModel;
import com.example.SPA_APPS.model.PriorityProdMstModel;
import com.example.SPA_APPS.repository.AreaInfoRepository;
import com.example.SPA_APPS.repository.PriorityProdRepository;
import com.example.SPA_APPS.service.PriorityProdService;
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
public class PriorityProdServiceImpl implements PriorityProdService {

    @Autowired
    private PriorityProdRepository priorityProdRepository;


    @Override
    public BaseResponse savePriorityProd(PriorityProdMstModel mstModel, List<PriorityProdDtlModel> dtlModels) {

        mstModel.setCreateDate(LocalDateTime.now());
        mstModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());




        PriorityProdMstModel savedMstModel = priorityProdRepository.savePriorityProdMst(mstModel);

        BaseResponse baseResponse=new BaseResponse();

        if (savedMstModel != null) {

            for (PriorityProdDtlModel dtlModel : dtlModels) {

                dtlModel.setMstId(savedMstModel.getMstId());
                dtlModel.setCreateDate(LocalDateTime.now());
                dtlModel.setCreateTerminal(IpAddressUtils.getLocalIpAddress());
                priorityProdRepository.savePriorityProdDtl(dtlModel);
                baseResponse.setMessage("Data saved successfully");
            }
        } else {
            // If master record is not saved, throw an exception or handle accordingly
            throw new RuntimeException("Failed to save the master record.");

        }
        return baseResponse;

    }

    @Override
    public BaseResponse searchPrioProdByMstId(Long id) {

        BaseResponse baseResponse=new BaseResponse();

        PriorityProdMstModel mstModel=priorityProdRepository.searchByMstId(id);
        baseResponse.setPriorityProdMstModel(mstModel);

        return  baseResponse;

    }

    @Override
    public BaseResponse updatePriorityProd(PriorityProdMstModel mstModel, List<PriorityProdDtlModel> dtlModels) {
        mstModel.setUpdateDate(LocalDateTime.now());
        mstModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());

        BaseResponse baseResponse = new BaseResponse();
        PriorityProdMstModel updatedMstModel = priorityProdRepository.updatePriorityProdMst(mstModel);

        if (updatedMstModel != null) {

            for (PriorityProdDtlModel dtlModel : dtlModels) {
                dtlModel.setMstId(updatedMstModel.getMstId());
                dtlModel.setUpdateDate(LocalDateTime.now());
                dtlModel.setUpdateTerminal(IpAddressUtils.getLocalIpAddress());

                priorityProdRepository.updatePriorityProdDtl(dtlModel);
            }

            baseResponse.setMessage("Data updated successfully");
        } else {
            throw new RuntimeException("Failed to update the master record.");
        }

        return baseResponse;
    }





}
