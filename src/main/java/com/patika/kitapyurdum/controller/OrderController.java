package com.patika.kitapyurdum.controller;

import com.patika.kitapyurdum.dto.request.OrderSaveRequest;
import com.patika.kitapyurdum.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void create(@RequestBody OrderSaveRequest request){
        orderService.create(request);
    }

}
