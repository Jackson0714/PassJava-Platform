package com.jackson0714.passjava.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.jackson0714.passjava.study.dao")
@SpringBootApplication
public class PassjavaStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassjavaStudyApplication.class, args);
    }
}
