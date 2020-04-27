package com.appventas.orderservice.repositories;

import com.appventas.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /* ejemplo como funciona similar a un query
    public List<Order> findOrderByAccountIdAndOrderId(String accountId, String OrderId);
    */
    public List<Order> findOrdersByAccountId(String accountId);
    public Order findOrderByOrderId(String orderId);
}
