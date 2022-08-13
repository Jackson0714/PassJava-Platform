package com.jackson0714.passjava.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 悟空聊架构
 * @site www.passjava.cn
 * @date 2022-08-01
 */
@Component
public class GlobalLoginFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange);
        /*
        只作为 demo，演示自定义全局过滤器的功能。
        ServerHttpRequest request= exchange.getRequest();
        String token = request.getHeaders().getFirst("access-token");
        //
        if(!StringUtils.isEmpty(token)){
            //if("admin".equals(token)){
                return chain.filter(exchange);
            //}
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();*/
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

