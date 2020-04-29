package com.jackson0714.passjava.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PassjavaThirdpartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassjavaThirdpartyApplication.class, args);
    }

}
