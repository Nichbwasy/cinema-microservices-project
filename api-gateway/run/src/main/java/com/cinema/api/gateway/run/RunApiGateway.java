package com.cinema.api.gateway.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.cinema.api.gateway"})
public class RunApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(RunApiGateway.class);
    }

}
