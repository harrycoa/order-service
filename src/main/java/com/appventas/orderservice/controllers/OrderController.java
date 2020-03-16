package com.appventas.orderservice.controllers;

import com.appventas.orderservice.dto.OrderRequest;
import com.appventas.orderservice.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {
    @GetMapping(value = "order")
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<OrderResponse> orderList = new ArrayList();

        // objeto 1
        OrderResponse response = new OrderResponse();
        response.setAccountId("100");
        response.setOrderId("001");
        response.setStatus("PENDIENTE");
        response.setTotalAmount(1000.00);
        response.setTotalTax(18.00);
        response.setTransactionDate(new Date());


        // objeto 2
        OrderResponse response2 = new OrderResponse();
        response2.setAccountId("102");
        response2.setOrderId("002");
        response2.setStatus("PENDIENTE");
        response2.setTotalAmount(1000.00);
        response2.setTotalTax(12.00);
        response2.setTransactionDate(new Date());

        // agregamos a una lista
        orderList.add(response);
        orderList.add(response2);



        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }


    @GetMapping(value = "order/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String orderId){
        OrderResponse response = new OrderResponse();
        response.setAccountId("100");
        response.setOrderId(orderId);
        response.setStatus("PENDIENTE");
        response.setTotalAmount(1000.00);
        response.setTotalTax(18.00);
        response.setTransactionDate(new Date());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value="order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest payload){

        OrderResponse response = new OrderResponse();
        response.setAccountId(payload.getAccountId());
        response.setOrderId("002");
        response.setStatus("PENDIENTE");
        response.setTotalAmount(1000.00);
        response.setTotalTax(18.00);
        response.setTransactionDate(new Date());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
