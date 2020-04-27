package com.appventas.orderservice.dao;
import com.appventas.orderservice.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
        List<Order> findAll();
        Optional<Order> findByOrderId(String orderId);
        Optional<Order> findById(Long id);
        Order save(Order order);
}
