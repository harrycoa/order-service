package com.appventas.orderservice.dto;

import com.appventas.orderservice.entities.Order;
import lombok.Data;

@Data
public class OrderDetailResponse {
    private long id;
    private int quantity;
    private double price;
    private double tax;
    private String upc;
    //private Order order;
}