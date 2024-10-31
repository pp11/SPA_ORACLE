package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.dto.TargetDto;
import com.example.SPA_APPS.entity.Target;
import com.example.SPA_APPS.exception.ResourceNotFoundException;
import com.example.SPA_APPS.model.TargetModel;
import com.example.SPA_APPS.repository.TargetRepository;
import com.example.SPA_APPS.service.TargetService;
import com.example.SPA_APPS.utils.BaseResponse;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetService {

    private final TargetRepository targetRepository;

    //private final MarketRepository marketRepository;


    @Override
    public BaseResponse saveTarget(TargetModel targetModel) {

        BaseResponse baseResponse = new BaseResponse();
        Optional<Target> existingTarget = targetRepository.findByMarketCodeAndProductCodeAndYearAndMonth(
                targetModel.getMarketCode(), targetModel.getProductCode(), targetModel.getYear(), targetModel.getMonth()
        );

//        List<MarketModel> existingMarket = marketRepository.getMarket(targetModel.getMarketCode());
//
//        if (existingMarket.isEmpty()){
//            baseResponse.setMessage("Invalid Market");
//            return baseResponse;
//        }

        if (existingTarget.isPresent()) {
            // If the record already exists, return an error response
            baseResponse.setMessage("Error: A target with the same marketCode, productCode, and year already exists.");
            return baseResponse;
        }
        Target target=new Target();
        BeanUtils.copyProperties(targetModel, target);
        target.setCreateDate(LocalDateTime.now());

        target.setTargetValue(target.getTargetQty()* target.getUnitTp());
        target = targetRepository.save(target);
        TargetDto targetDto  = entityToDto(target);
        baseResponse.setMessage("data save successfully");
        baseResponse.setTargetDto(targetDto);
        return baseResponse;
    }

    @Override
    public BaseResponse updateTarget(TargetModel targetModel) {
        BaseResponse baseResponse = new BaseResponse();

        Target target = targetRepository.findTargetById(targetModel.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Target details Not Found by this id==> "+targetModel.getId()));

        target = modelToEntity(target,targetModel);

        target.setUpdateDate(LocalDateTime.now());
        target.setTargetValue(target.getTargetQty()* target.getUnitTp());
        target = targetRepository.save(target);

        TargetDto targetDto = entityToDto(target);

        baseResponse.setTargetDto(targetDto);
        baseResponse.setMessage("data updated successfully");

        return baseResponse;
    }

    @Override
    public BaseResponse deleteTarget(Long id) {
        BaseResponse baseResponse=new BaseResponse();
        if (targetRepository.existsById(id)) {
            targetRepository.deleteById(id);
        }
        baseResponse.setMessage("success");

        return baseResponse;
    }
//
//    @Override
//    public List<MarketModel> getMarket(String marketCode) {
//        List<MarketModel> modelList = null;
//        try {
//            modelList = marketRepository.getMarket(marketCode);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return modelList;
//    }

    @Override
    public BaseResponse saveFromExcel(MultipartFile file) throws IOException {

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Target> targets = new ArrayList<>();

            for (Row row : sheet) {
                // Skip the header row (if present)
                if (row.getRowNum() == 0) {
                    continue;
                }

                Target target = new Target();
                target.setYear(row.getCell(0).getStringCellValue());
                target.setMonth(row.getCell(1).getStringCellValue());
                target.setMarketCode(row.getCell(2).getStringCellValue());
                target.setMarketName(row.getCell(3).getStringCellValue());
                target.setProductCode(row.getCell(4).getStringCellValue());
                target.setProductName(row.getCell(5).getStringCellValue());
                target.setPackSize(row.getCell(6).getStringCellValue());
                target.setUnitTp(row.getCell(7).getNumericCellValue());
                target.setTargetQty(row.getCell(8).getNumericCellValue());
                // target.setTargetValue(row.getCell(9).getNumericCellValue());
                target.setTargetValue(row.getCell(8).getNumericCellValue()*row.getCell(7).getNumericCellValue());
                target.setCreateDate(LocalDateTime.now());
                target.setUpdateDate(LocalDateTime.now());

                targets.add(target);
            }

            targetRepository.saveAll(targets);
//            List<TargetDto> targetDtos = new ArrayList<>();
//             targetDtos  = entitiesToDtos(targets);

        }


        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setMessage("success");
        return  baseResponse;

    }

    @Override
    public BaseResponse findTargetDetails(String year, String month) {
        BaseResponse baseResponse=new BaseResponse();
        baseResponse.setMessage("success");

        List<TargetDto> targets = targetRepository.findByYearAndMonth(year,month)
                .stream().map(this::entityToDto).toList();

        baseResponse.setTargetDtos(targets);
        baseResponse.setMessage("a");
        return baseResponse;

    }

    @Override
    public BaseResponse findByAnyInfo(TargetModel targetModel) {
        BaseResponse baseResponse = new BaseResponse();
        Specification<Target> specification = searchByCriteria(targetModel);
        List<TargetDto> targetDto = targetRepository.findAll(specification)
                .stream().map(this::entityToDto).toList();

        baseResponse.setTargetDtos(targetDto);
        baseResponse.setMessage("success");

        return baseResponse;
    }




    private TargetDto entityToDto(Target target) {
        TargetDto targetDto = new TargetDto();
        BeanUtils.copyProperties(target, targetDto);
        return targetDto;
    }
//    private List<TargetDto> entitiesToDtos(List<Target> targets) {
//        return targets.stream()
//                .map(this::entityToDto) // Use the single entity conversion method
//                .collect(Collectors.toList());
//    }

    public Specification<Target> searchByCriteria(TargetModel criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getYear() != null) {
                predicates.add(criteriaBuilder.equal(root.get("year"), criteria.getYear()));
            }

            if (criteria.getMonth() != null) {
                predicates.add(criteriaBuilder.equal(root.get("month"), criteria.getMonth()));
            }

            if (criteria.getProductCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("productCode"), criteria.getProductCode()));
            }
            if (criteria.getProductName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("productName"), criteria.getProductName()));
            }
            if (criteria.getMarketCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("marketCode"), criteria.getMarketCode()));
            }
            if (criteria.getMarketName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("marketName"), criteria.getMarketName()));
            };

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    private Target modelToEntity(Target target, TargetModel targetModel) {

        if(target != null) {
            if (target.getTargetQty()!=null) target.setTargetQty(targetModel.getTargetQty());
            if (target.getUnitTp()!=null) target.setUnitTp(targetModel.getUnitTp());

        }
        return  target;
    }






}