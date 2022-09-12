package com.bridgelabz.fundoonoteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FundooNoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundooNoteServiceApplication.class, args);
    }

}
