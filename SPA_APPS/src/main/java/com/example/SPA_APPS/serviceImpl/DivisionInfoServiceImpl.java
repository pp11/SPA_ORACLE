package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.dto.DivisionInfoDto;
import com.example.SPA_APPS.entity.DivisionInfo;
import com.example.SPA_APPS.exception.ResourceNotFoundException;
import com.example.SPA_APPS.model.DivisionInfoModel;
import com.example.SPA_APPS.repository.DivisionInfoRepository;
import com.example.SPA_APPS.service.DivisionInfoService;
import com.example.SPA_APPS.utils.BaseResponse;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DivisionInfoServiceImpl implements DivisionInfoService {

    private final DivisionInfoRepository divisionInfoRepository;



    @Override
    public BaseResponse saveDivisionInfo(DivisionInfoModel divisionInfoModel) {
        BaseResponse baseResponse = new BaseResponse();

        Optional<DivisionInfo> existingDivision = divisionInfoRepository.findByDivisionCode(
                divisionInfoModel.getDivisionCode());

        if (existingDivision.isPresent()) {
            // If the record already exists, return an error response
            baseResponse.setMessage("Error: Division code is already exists");
            return baseResponse;
        }
        DivisionInfo divisionInfo=new DivisionInfo();
        BeanUtils.copyProperties(divisionInfoModel, divisionInfo);
        divisionInfo.setCreateDate(LocalDateTime.now());


        divisionInfo = divisionInfoRepository.save(divisionInfo);
        DivisionInfoDto divisionInfoDto  = entityToDto(divisionInfo);
        baseResponse.setMessage("Data save successfully");
        baseResponse.setDivisionInfoDto(divisionInfoDto);
        return baseResponse;
    }

    @Override
    public BaseResponse updateDivisionInfo(DivisionInfoModel divisionInfoModel) {


        BaseResponse baseResponse = new BaseResponse();

        DivisionInfo divisionInfo = divisionInfoRepository.findById(divisionInfoModel.getId())
                .orElseThrow(() -> new ResourceNotFoundException("data Not Found by this id==> "+divisionInfoModel.getId()));

        divisionInfo = modelToEntity(divisionInfo,divisionInfoModel);

        divisionInfo.setUpdateDate(LocalDateTime.now());

        divisionInfo = divisionInfoRepository.save(divisionInfo);

        DivisionInfoDto divisionInfoDto = entityToDto(divisionInfo);

        baseResponse.setDivisionInfoDto(divisionInfoDto);
        baseResponse.setMessage("data updated successfully");

        return baseResponse;
    }

    @Override
    public BaseResponse findByAnyInfo(DivisionInfoModel divisionInfoModel) {
        BaseResponse baseResponse = new BaseResponse();
        Specification<DivisionInfo> specification = searchByCriteria(divisionInfoModel);
        List<DivisionInfoDto> divisionInfoDtos = divisionInfoRepository.findAll(specification)
                .stream().map(this::entityToDto).toList();

        baseResponse.setDivisionInfoDtos(divisionInfoDtos);
        baseResponse.setMessage("success");

        return baseResponse;
    }

    @Override
    public BaseResponse deleteDivision(Long id) {
        BaseResponse baseResponse=new BaseResponse();
        if (divisionInfoRepository.existsById(id)) {
            divisionInfoRepository.deleteById(id);
        }
        baseResponse.setMessage("data deleted successfully");

        return baseResponse;
    }

    private DivisionInfoDto entityToDto(DivisionInfo divisionInfo) {
        DivisionInfoDto divisionInfoDto = new DivisionInfoDto();
        BeanUtils.copyProperties(divisionInfo, divisionInfoDto);
        return divisionInfoDto;
    }

    private DivisionInfo modelToEntity(DivisionInfo divisionInfo, DivisionInfoModel divisionInfoModel) {

        if(divisionInfoModel != null) {
            if (divisionInfo.getDivisionName()!=null) divisionInfo.setDivisionName(divisionInfoModel.getDivisionName());
            if (divisionInfo.getStatus()!=null) divisionInfo.setStatus(divisionInfoModel.getStatus());

        }
        return  divisionInfo;
    }


    public Specification<DivisionInfo> searchByCriteria(DivisionInfoModel criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), criteria.getId()));
            }

            if (criteria.getDivisionCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("divisionCode"), criteria.getDivisionCode()));
            }

            if (criteria.getDivisionName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("divisionName"), criteria.getDivisionName()));
            }

            if (criteria.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
