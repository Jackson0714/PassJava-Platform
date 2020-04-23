package com.jackson0714.passjava.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PassjavaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassjavaGatewayApplication.class, args);
    }

}
