package com.cinema.films.microservice.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {"com.cinema.films.microservice"})
@ComponentScan(basePackages = {"com.cinema.films.microservice"})
@EnableJpaRepositories(basePackages = {"com.cinema.films.microservice"})
public class RunFilmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunFilmsApplication.class);
    }
}
