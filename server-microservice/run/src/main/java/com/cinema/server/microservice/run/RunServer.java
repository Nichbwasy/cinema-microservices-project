package com.cinema.server.microservice.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaServer
@SpringBootApplication
@EntityScan(basePackages = "com.cinema.server.microservice")
@ComponentScan(basePackages = {"com.cinema.server.microservice"})
public class RunServer {

    public static void main(String[] args) {
        SpringApplication.run(RunServer.class);
    }

}