package com.jackson0714.passjava.gateway.config;

import com.alibaba.fastjson.JSON;
import com.jackson0714.passjava.gateway.common.ResponseCodeEnum;
import com.jackson0714.passjava.gateway.common.ResponseResult;
import com.jackson0714.passjava.gateway.utils.PassJavaJwtTokenUtil;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author wukong
 */
@Configuration
public class JwtAuthCheckFilter {
    private static final String TOKEN = "token";
    @Resource
    private PassJavaJwtProperties jwtProperties;
    @Resource
    private PassJavaJwtTokenUtil jwtTokenUtil;

    @Bean
    @Order(-101)
    public GlobalFilter jwtAuthGlobalFilter() {

        return (exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            String requestUrl = serverHttpRequest.getURI().getPath();

            if(!TOKEN.equals(requestUrl)){
                //从HTTP请求头中获取JWT令牌
                String jwtToken = serverHttpRequest
                        .getHeaders()
                        .getFirst(jwtProperties.getHeader());
                // 对Token解签名，并验证Token是否过期
                boolean isJwtNotValid = jwtTokenUtil.isTokenExpired(jwtToken);
                if(isJwtNotValid){ //如果JWT令牌不合法
                    return writeUnAuthorizedMessageAsJson(serverHttpResponse);
                }
                //从JWT中解析出当前用户的身份（userId），并继续执行过滤器链，转发请求
                ServerHttpRequest mutableReq = serverHttpRequest
                        .mutate()
                        .header("userId", jwtTokenUtil.getUsernameFromToken(jwtToken))
                        .build();
                ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                return chain.filter(mutableExchange);
            }else{ //如果是登录认证请求，直接执行不需要进行JWT权限验证
                return chain.filter(exchange);
            }
        };
    }

    /**
     * 将JWT鉴权失败的消息响应给客户端
     */
    private Mono<Void> writeUnAuthorizedMessageAsJson(ServerHttpResponse serverHttpResponse) {
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ResponseResult responseResult = ResponseResult.error(ResponseCodeEnum.TOKEN_INVALID.getCode(),
                ResponseCodeEnum.TOKEN_INVALID.getMessage());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory()
                .wrap(JSON.toJSONStringWithDateFormat(responseResult, JSON.DEFFAULT_DATE_FORMAT)
                        .getBytes(StandardCharsets.UTF_8));
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

}
