package com.mas.e_commerce_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}, scanBasePackages ={
        "com.mas.e_commerce_back"
} )
public class ECommerceBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceBackApplication.class, args);
    }

}
