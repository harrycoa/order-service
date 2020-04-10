package com.appventas.orderservice.handler;

import com.appventas.orderservice.exception.AccountNotFoundException;
import com.appventas.orderservice.exception.IncorrectOrderRequestException;
import com.appventas.orderservice.exception.NotImplementedRequestException;
import com.appventas.orderservice.exception.OrderServiceExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class OrderServiceExceptionHandler extends ResponseEntityExceptionHandler {
    /************ Control de Excepciones  *************/
    // Controlador de excepciones para todos los dto Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception exception, WebRequest request){
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());

        return new ResponseEntity<>(response, response.getStatus());
    }

    // controlador de excepciones peticion incorrecta
    @ExceptionHandler(IncorrectOrderRequestException.class)
    public ResponseEntity<Object> handleIncorrectRequest(Exception exception, WebRequest request){
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST, LocalDateTime.now());

        return new ResponseEntity<>(response, response.getStatus());
    }

    // controlador de excepciones registro no encontrado
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(Exception exception, WebRequest request){
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND, LocalDateTime.now());

        return new ResponseEntity<>(response, response.getStatus());
    }
    // implementar mas controles de exceptcion
    // controlador de excepciones no implementado
    @ExceptionHandler(NotImplementedRequestException.class)
    public ResponseEntity<Object> handleNotImplemented(Exception exception, WebRequest request){
        OrderServiceExceptionResponse response = new OrderServiceExceptionResponse(exception.getMessage(),
                request.getDescription(false), HttpStatus.NOT_IMPLEMENTED, LocalDateTime.now());

        return new ResponseEntity<>(response, response.getStatus());
    }

}
