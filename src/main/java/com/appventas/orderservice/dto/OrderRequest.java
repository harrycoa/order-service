package com.appventas.orderservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ApiModel(description = "Este payload representa una clase que sera procesada")
public class OrderRequest {
    @NotNull
    @NotBlank
    @ApiModelProperty(notes = "Account Id", example = "123456", required = true)
    public String accountId;

    private List<LineItem> items;

}
