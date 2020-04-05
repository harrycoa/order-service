package com.appventas.orderservice.service;

import com.appventas.orderservice.client.CustomerServiceClient;
import com.appventas.orderservice.dto.AccountDto;
import com.appventas.orderservice.dto.OrderRequest;
import com.appventas.orderservice.dto.OrderResponse;
import com.appventas.orderservice.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private CustomerServiceClient customerClient;
    public Order createOrder(OrderRequest orderRequest){
        AccountDto account = customerClient.findAccountById(orderRequest.getAccountId());
        AccountDto dummyAccount = customerClient.createDummyAccount();
        //dummyAccount = customerClient.createAccount(dummyAccount);

        dummyAccount = customerClient.createAccountBody(dummyAccount);
        dummyAccount.getAddress().setZipCode("+5151");
        customerClient.updateAccount(dummyAccount);
        AccountDto updateAccount = customerClient.findAccountById(orderRequest.getAccountId());
        log.info(updateAccount.toString());

        customerClient.deleteAccount(dummyAccount);

        Order response = new Order();
        response.setAccountId(orderRequest.getAccountId());
        response.setOrderId("002");
        response.setStatus("PENDIENTE");
        response.setTotalAmount(1000.00);
        response.setTotalTax(18.00);
        response.setTransactionDate(new Date());

        return response;
    }

    public List<Order> findAllOrders(){
        List<Order> orderList = new ArrayList();

        // objeto 1
        Order response = new Order();
        response.setAccountId("100");
        response.setOrderId("001");
        response.setStatus("PENDIENTE");
        response.setTotalAmount(1000.00);
        response.setTotalTax(18.00);
        response.setTransactionDate(new Date());


        // objeto 2
        Order response2 = new Order();
        response2.setAccountId("102");
        response2.setOrderId("002");
        response2.setStatus("PENDIENTE");
        response2.setTotalAmount(1000.00);
        response2.setTotalTax(12.00);
        response2.setTransactionDate(new Date());

        // agregamos a una lista
        orderList.add(response);
        orderList.add(response2);

        return orderList;
    }

    public Order findOrderById(String orderId){
        Order response = new Order();
        response.setAccountId("100");
        response.setOrderId(orderId);
        response.setStatus("PENDIENTE");
        response.setTotalAmount(1000.00);
        response.setTotalTax(18.00);
        response.setTransactionDate(new Date());

        return response;
    }

}
