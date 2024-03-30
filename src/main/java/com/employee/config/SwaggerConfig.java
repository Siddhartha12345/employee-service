package com.employee.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI employeeOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("EMS-EMPLOYEE-SERVICE API")
                        .description("Employee microservice responsible for dealing with employee-related operations")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .termsOfService("terms")
                        .contact(new Contact().email("siddharthapaul303@gmail.com")));
    }
}
