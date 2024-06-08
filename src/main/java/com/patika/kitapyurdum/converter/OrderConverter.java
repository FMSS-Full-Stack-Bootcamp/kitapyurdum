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
        Order order = new Order();
        order.setCreateDate(LocalDateTime.now());
        order.setCustomerId(customerId);
        order.setOrderStatus(OrderStatus.INITIAL);
        order.setOrderCode("order-code" + request.getCustomerId());
        order.setProductList(productList);
        return order;
    }
}
