package com.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EMSEmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EMSEmployeeServiceApplication.class, args);
	}

}
