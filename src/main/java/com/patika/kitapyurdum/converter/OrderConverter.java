package com.patika.kitapyurdum.converter;

import com.patika.kitapyurdum.dto.request.OrderSaveRequest;
import com.patika.kitapyurdum.model.Order;
import com.patika.kitapyurdum.model.Product;
import com.patika.kitapyurdum.model.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order convert(OrderSaveRequest request, Long customerId, List<Product> productList) {
        return Order.builder()
                .createDate(LocalDateTime.now())
                .customerId(customerId)
                .orderStatus(OrderStatus.INITIAL)
                .orderCode("order-code" + request.getCustomerId())
                .productList(productList)
                .build();
    }
}
