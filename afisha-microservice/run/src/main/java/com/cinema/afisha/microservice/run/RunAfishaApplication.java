package com.cinema.afisha.microservice.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {"com.cinema.afisha.microservice"})
@ComponentScan(basePackages = {
        "com.cinema.afisha.microservice",
        "com.cinema.cinemas.microservice.clients",
        "com.cinema.films.microservice.clients"
})
@EnableJpaRepositories(basePackages = {"com.cinema.afisha.microservice"})
public class RunAfishaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunAfishaApplication.class);
    }

}
