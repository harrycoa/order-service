package com.appventas.orderservice.util;

public enum ExceptionMessagesEnum {

    ACCOUNT_NOT_FOUND("Cuenta no encontrada"),
    INCORRECT_REQUEST_EMPTY_ITEMS_ORDER("Lista vacia");

    ExceptionMessagesEnum(String msg){
        value = msg;
    }

    private final String value;

    public String getValue(){
        return value;
    }

}
