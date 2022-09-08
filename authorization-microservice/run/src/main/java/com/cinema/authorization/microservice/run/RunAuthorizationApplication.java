package com.cinema.authorization.microservice.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {"com.cinema.authorization.microservice"})
@EnableJpaRepositories(basePackages = {"com.cinema.authorization.microservice"})
@ComponentScan(basePackages = {"com.cinema.authorization.microservice"})
public class RunAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunAuthorizationApplication.class);
    }

}
