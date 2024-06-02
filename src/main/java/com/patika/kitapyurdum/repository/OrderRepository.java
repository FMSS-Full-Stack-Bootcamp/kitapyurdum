package com.patika.kitapyurdum.repository;

import com.patika.kitapyurdum.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private List<Order> orderList = new ArrayList<>();

    public void save(Order order) {
        orderList.add(order);
    }
}
