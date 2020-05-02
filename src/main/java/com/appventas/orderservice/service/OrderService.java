package com.appventas.orderservice.service;

import com.appventas.orderservice.client.CustomerServiceClient;
import com.appventas.orderservice.dao.JpaOrderDAO;
import com.appventas.orderservice.dto.*;
import com.appventas.orderservice.entities.Order;
import com.appventas.orderservice.entities.OrderDetail;
import com.appventas.orderservice.exception.AccountNotFoundException;
import com.appventas.orderservice.exception.OrderNotFoundException;
import com.appventas.orderservice.exception.PaymentNotAcceptedException;
import com.appventas.orderservice.repositories.OrderRepository;
import com.appventas.orderservice.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.appventas.orderservice.client.InventoryServiceClient;
import com.appventas.orderservice.producer.ShippingOrderProducer;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    // Microservicio customer
    @Autowired
    private CustomerServiceClient customerClient;

    //Sin Repositorio trabaja con JPA
    //@Autowired
    //private JpaOrderDAO jpaOrderDAO;

    // vincular el repositorio
    @Autowired
    private OrderRepository orderRepository;

    // Micorservicio Payment
    @Autowired
    private PaymentProcessorService  paymentService;

    // Microservicio inventory
    @Autowired
    private InventoryServiceClient inventoryClient;

    // Microservicio Shipping
    @Autowired
    private ShippingOrderProducer shipmentMessageProducer;

    // MAiling
    @Autowired
    private OrderMailService mailService;


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Order createOrder(OrderRequest orderRequest) throws PaymentNotAcceptedException {

        OrderValidator.validateOrder(orderRequest);

        AccountDto account = customerClient.findAccount(orderRequest.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessagesEnum.ACCOUNT_NOT_FOUND.getValue()));

        Order newOrder = initOrder(orderRequest);
        //return orderDAO.save(newOrder);
        //return jpaOrderDAO.save(newOrder);

        //Confirmation confirmation = paymentService.processPayment(newOrder, account);

        Confirmation confirmation = paymentService.processPayment(newOrder,account);

        log.info("Payment Confirmation: {}", confirmation);

        String paymentStatus = confirmation.getTransactionStatus();

        newOrder.setPaymentStatus(OrderPaymentStatus.valueOf(paymentStatus));

        if (paymentStatus.equals(OrderPaymentStatus.DENIED.name())) {
            newOrder.setStatus(OrderStatus.NA);
            orderRepository.save(newOrder);
            throw new PaymentNotAcceptedException("The Payment added to your account was not accepted, please verify.");
        }

        log.info("Updating Inventory: {}", orderRequest.getItems());
        inventoryClient.updateInventory(orderRequest.getItems());

        log.info("Sending Request to Shipping Service.");
        shipmentMessageProducer.send(newOrder.getOrderId(), account);

        return orderRepository.save(newOrder);
    }

    private Order initOrder(OrderRequest orderRequest){
        Order orderObj = new Order();
        orderObj.setOrderId(UUID.randomUUID().toString());
        orderObj.setAccountId(orderRequest.getAccountId());
        orderObj.setStatus(OrderStatus.PENDING);


        List<OrderDetail> orderDetails = orderRequest.getItems().stream()
                .map(item -> OrderDetail.builder()
                            .price(item.getPrice())
                            .quantity(item.getQuantity())
                            .upc(item.getUpc())
                            .tax((item.getQuantity() * item.getPrice() * Constants.TAX_IMPORT))
                            .totalAmount((item.getPrice()*item.getQuantity()))
                            .order(orderObj).build())
                .collect(Collectors.toList());

        orderObj.setDetails(orderDetails);
        //orderObj.setTotalAmount(orderDetails.stream().mapToDouble(OrderDetail::getPrice).sum());
        orderObj.setTotalAmount(orderDetails.stream().mapToDouble(OrderDetail::getTotalAmount).sum());
        orderObj.setTotalTax(orderObj.getTotalAmount() * Constants.TAX_IMPORT);
        orderObj.setTotalAmountTax(orderObj.getTotalAmount() + orderObj.getTotalTax());
        orderObj.setTransactionDate(new Date());
        return orderObj;

    }





    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }

    public Order findOrderById(String orderId){
        Optional<Order> order = Optional.ofNullable(orderRepository.findOrderByOrderId(orderId));
        return order
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public Order findById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }


    public List<Order> finOrdersByAccountId(String accountId){
        Optional<List<Order>> orders = Optional.ofNullable(orderRepository.findOrdersByAccountId(accountId));
        return orders
                .orElseThrow(() -> new OrderNotFoundException("Order were not found"));
    }

    public void updateShipmentOrder(ShipmentOrderResponse response) {
        try {
            Order order = findOrderById(response.getOrderId());
            order.setStatus(OrderStatus.valueOf(response.getShippingStatus()));
            orderRepository.save(order);
            mailService.sendEmail(order, response);
        }
        catch(OrderNotFoundException orderNotFound) {
            log.info("The Following Order was not found: {} with tracking Id: {}", response.getOrderId(), response.getTrackingId());
        }
        catch(Exception e) {
            log.info("An error occurred sending email: " + e.getMessage());
        }
    }

    /* Reemplazamos los DAO por repository, ya que contamos con una clase repository
    public List<Order> findAllOrders(){
        return jpaOrderDAO.findAll();
    }

    public Order findOrderById(String orderId){
        return jpaOrderDAO.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public Order findById(Long id){
        return jpaOrderDAO.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }
    */
}
