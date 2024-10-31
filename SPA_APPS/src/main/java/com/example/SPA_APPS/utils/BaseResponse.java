package com.example.SPA_APPS.utils;

import com.example.SPA_APPS.dto.TargetDto;
import lombok.Data;

import java.util.List;



@Data
public class BaseResponse {
    private TargetDto targetDto;
    private List<TargetDto> targetDtos;

    private String message;

}
