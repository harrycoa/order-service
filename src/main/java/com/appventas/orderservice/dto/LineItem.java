package com.appventas.orderservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Clase representa un item incluido en la orden")
public class LineItem {
    @ApiModelProperty(notes = "UPC codigo universal de un producto 12 digitos", example = "123456789123", required = true, position = 0)
    private String upc;
    @ApiModelProperty(notes = "quantity cantidad", example = "5", required = true, position = 1)
    private int quantity;
    @ApiModelProperty(notes = "price precio del producto", example = "15.7", required = true, position = 2)
    private double price;
}
