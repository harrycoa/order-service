package com.appventas.orderservice.client;

import com.appventas.orderservice.config.OrderServiceConfig;
import com.appventas.orderservice.dto.AccountDto;
import com.appventas.orderservice.dto.AddressDto;
import com.appventas.orderservice.dto.CreditCardDto;
import com.appventas.orderservice.dto.CustomerDto;
import com.appventas.orderservice.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerServiceClient {
    // este componente invocara el otro serivicoi utilizando resttemplate
    private RestTemplate restTemplate;
    @Autowired
    private OrderServiceConfig config;

    private CustomerServiceClient(RestTemplateBuilder builder){
            restTemplate = builder.build();
    }

    public AccountDto findAccountById(String accountId){
        AccountDto account =  restTemplate.getForObject(config.getCustomerServiceUrl() + "/{id}", AccountDto.class, accountId);
        return account;
    }

    public AccountDto createDummyAccount(){
        AddressDto address = AddressDto.builder().street("Av. El Sol")
                                                    .city("Cusco")
                                                    .state("Cusco")
                                                    .country("Peru")
                                                    .zipCode("51")
                                                    .build();

        CustomerDto customer = CustomerDto.builder().lastName("Coa")
                                                    .firstName("Harry")
                                                    .email("rharrycoa@gmail.com")
                                                    .build();

        CreditCardDto creditCard = CreditCardDto.builder().nameOnCard("harry")
                                                    .number("4245785845841")
                                                    .expirationMonth("03")
                                                    .expirationYear("2024")
                                                    .type("VISA")
                                                    .ccv("314")
                                                    .build();

        AccountDto account = AccountDto.builder().address(address)
                                                .customer(customer)
                                                .creditCard(creditCard)
                                                .status(AccountStatus.ACTIVE)
                                                .build();
        return account;
    }
    public AccountDto createAccount(AccountDto account){
        return restTemplate.postForObject(config.getCustomerServiceUrl(), account, AccountDto.class );
    }

}
