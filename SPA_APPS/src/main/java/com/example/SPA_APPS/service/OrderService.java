package com.example.SPA_APPS.service;

import com.example.SPA_APPS.model.OrderRequestModel;
import com.example.SPA_APPS.utils.BaseResponse;

public interface OrderService {
    public BaseResponse processOrder(OrderRequestModel orderRequestModel);
}
