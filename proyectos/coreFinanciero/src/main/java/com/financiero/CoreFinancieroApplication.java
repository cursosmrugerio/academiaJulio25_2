package com.financiero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CoreFinancieroApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreFinancieroApplication.class, args);
    }
}