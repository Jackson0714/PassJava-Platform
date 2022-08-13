package com.jackson0714.passjava.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author wukong
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.jackson0714.passjava"})
public class PassjavaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassjavaAuthApplication.class, args);
    }

}
