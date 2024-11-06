package com.example.SPA_APPS.controller;

import com.example.SPA_APPS.model.OrderRequestModel;
import com.example.SPA_APPS.service.OrderService;
import com.example.SPA_APPS.utils.BaseResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spa/api/v1/Transaction")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService service) {
        this.orderService = service;
    }
    @PostMapping("/orderProcess")
    public BaseResponse processOrder(@RequestBody OrderRequestModel orderRequestModel) {
        return orderService.processOrder(orderRequestModel);
    }
}
