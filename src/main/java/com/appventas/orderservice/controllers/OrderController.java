package com.appventas.orderservice.controllers;

import com.appventas.orderservice.dto.OrderRequest;
import com.appventas.orderservice.dto.OrderResponse;
import com.appventas.orderservice.entities.Order;
import com.appventas.orderservice.service.OrderService;
import com.appventas.orderservice.util.EntityDtoConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private EntityDtoConverter converter;

    @ApiOperation(value = "lista las ventas", notes= "esta operacion lista las ventas")
    @GetMapping(value = "order")
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<Order> orders = orderService.findAllOrders();
        return new ResponseEntity<>(converter.convertEntityToDto(orders), HttpStatus.OK);
    }

    @ApiOperation(value = "busca una venta", notes= "esta operacion busca una venta por id")
    @GetMapping(value = "order/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String orderId){
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(converter.convertEntityToDto(order), HttpStatus.OK);
    }

    @ApiOperation(value = "Crea una venta", notes= "esta operacion crea una venta")
    @PostMapping(value="order/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest payload){
        Order order = orderService.createOrder(payload);
        return new ResponseEntity<>(converter.convertEntityToDto(order), HttpStatus.OK);
    }

}
