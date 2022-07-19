package com.cinema.cinemas.microservice.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {"com.cinema.cinemas.microservice"})
@ComponentScan(basePackages = {"com.cinema.cinemas.microservice"})
@EnableJpaRepositories(basePackages = {"com.cinema.cinemas.microservice"})
public class RunCinemasApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunCinemasApplication.class);
    }

}
