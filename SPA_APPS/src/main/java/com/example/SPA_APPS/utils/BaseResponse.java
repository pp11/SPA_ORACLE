package com.example.SPA_APPS.utils;

import com.example.SPA_APPS.dto.DivisionInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class BaseResponse {
    private DivisionInfoDto divisionInfoDto;
    private List<DivisionInfoDto> divisionInfoDtos;
    private String message;

}
