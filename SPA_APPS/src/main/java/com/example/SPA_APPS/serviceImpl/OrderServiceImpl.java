package com.example.SPA_APPS.serviceImpl;

import com.example.SPA_APPS.model.OrderRequestModel;
import com.example.SPA_APPS.repository.OrderRepository;
import com.example.SPA_APPS.service.OrderService;
import com.example.SPA_APPS.utils.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;


    @Override
    public BaseResponse processOrder(OrderRequestModel orderRequest) {
        BaseResponse response = new BaseResponse();
        try {
            String orderNo = repository.processOrder(orderRequest);
            if ("ORDER_NOT_FOUND".equals(orderNo)) {
                response.setMessage("No data found for the given registration number.");
            } else {
                response.setMessage("Order processed successfully. Order No: " + orderNo);
            }
        } catch (Exception e) {
            response.setMessage("Order processing failed: " + e.getMessage());
        }
        return response;
    }
}
