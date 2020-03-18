package com.appventas.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    /*
    private ApiInfo getApiInfo(){
        return new ApiInfo("appVentas Api Service",
                            "Esta es una prueba de como se construye la info de la API" ,
                            "1.0",
                            "Terminos de uso",
                             new Contact(name:"haryr", url:"http//cuscosaas.com", email: "rharryc@gmail.com"),
                             license:"Licencia", licenseUrl:"Licicicic"),Collection.emptyList());
    }*/
}
