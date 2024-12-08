package com.example.SPA_APPS.service;
import com.example.SPA_APPS.model.LocationMappingModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface LocationMappingService {

    public BaseResponse saveLocation(LocationMappingModel locationMappingModel);

    public BaseResponse updateLocation(LocationMappingModel locationMappingModel);


}
