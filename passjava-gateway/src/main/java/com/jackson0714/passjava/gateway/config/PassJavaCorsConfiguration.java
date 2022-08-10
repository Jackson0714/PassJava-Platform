package com.jackson0714.passjava.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class PassJavaCorsConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 配置跨域
        // 允许所有请求头跨域
        corsConfiguration.addAllowedHeader("*");
        // 允许所有请求方法跨域
        corsConfiguration.addAllowedMethod("*");
        // 允许所有请求来源跨域
        corsConfiguration.addAllowedOrigin("*");
        //允许携带cookie跨域，否则跨域请求会丢失cookie信息
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}
