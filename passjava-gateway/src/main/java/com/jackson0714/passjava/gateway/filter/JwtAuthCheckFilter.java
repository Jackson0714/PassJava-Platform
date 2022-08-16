package com.jackson0714.passjava.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.jackson0714.passjava.common.constant.Constants;
import com.jackson0714.passjava.common.constant.TokenConstants;
import com.jackson0714.passjava.common.utils.StringUtils;
import com.jackson0714.passjava.jwt.common.ResponseCodeEnum;
import com.jackson0714.passjava.jwt.common.ResponseResult;
import com.jackson0714.passjava.jwt.config.PassJavaJwtProperties;
import com.jackson0714.passjava.jwt.utils.PassJavaJwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author wukong
 */
@Slf4j
@Configuration
public class JwtAuthCheckFilter {
    private static final String AUTH_TOKEN_URL = "/auth/login";
    private static final String RENREN_CAPTCHA_TOKEN_URL = "/renren-fast/captcha.jpg";
    private static final String RENREN_LOGIN_TOKEN_URL = "/renren-fast/sys/login";
    private static final String RENREN_START_URL = "/renren-fast";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String FROM_SOURCE = "from-source";

    @Resource
    private PassJavaJwtProperties jwtProperties;
    @Resource
    private PassJavaJwtTokenUtil jwtTokenUtil;

    @Bean
    @Order(-101)
    public GlobalFilter jwtAuthGlobalFilter() {
        return (exchange, chain) -> {

            // renren-fast 自带了 token 认证，所以 Gateway 不需要做登录认证了，跳过 token 验证，转发所有请求。
            boolean flag = true;
            if(flag) {
                return chain.filter(exchange);
            }

            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            ServerHttpRequest.Builder mutate = serverHttpRequest.mutate();
            String requestUrl = serverHttpRequest.getURI().getPath();

            // 跳过对登录请求的 token 检查。因为登录请求是没有 token 的，是来申请 token 的。
            if(AUTH_TOKEN_URL.equals(requestUrl)) {
                return chain.filter(exchange);
            }

            // 从 HTTP 请求头中获取 JWT 令牌
            String token = getToken(serverHttpRequest);
            if (StringUtils.isEmpty(token)) {
                return unauthorizedResponse(exchange, serverHttpResponse, ResponseCodeEnum.TOKEN_MISSION);
            }

            // 对Token解签名，并验证Token是否过期
            boolean isJwtNotValid = jwtTokenUtil.isTokenExpired(token);
            if(isJwtNotValid){
                return unauthorizedResponse(exchange, serverHttpResponse, ResponseCodeEnum.TOKEN_INVALID);
            }
            // 验证 token 里面的 userId 是否为空
            String userId = jwtTokenUtil.getUserIdFromToken(token);
            String username = jwtTokenUtil.getUserNameFromToken(token);
            if (StringUtils.isEmpty(userId)) {
                return unauthorizedResponse(exchange, serverHttpResponse, ResponseCodeEnum.TOKEN_CHECK_INFO_FAILED);
            }

            // 设置用户信息到请求
            addHeader(mutate, USER_ID, userId);
            addHeader(mutate, USER_NAME, username);
            // 内部请求来源参数清除
            removeHeader(mutate, FROM_SOURCE);
            return chain.filter(exchange.mutate().request(mutate.build()).build());
        };
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name) {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, Constants.UTF8);
        }
        catch (UnsupportedEncodingException e)
        {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(jwtProperties.getHeader());
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX))
        {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    /**
     * 将 JWT 鉴权失败的消息响应给客户端
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ServerHttpResponse serverHttpResponse, ResponseCodeEnum responseCodeEnum) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ResponseResult responseResult = ResponseResult.error(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory()
                .wrap(JSON.toJSONStringWithDateFormat(responseResult, JSON.DEFFAULT_DATE_FORMAT)
                        .getBytes(StandardCharsets.UTF_8));
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

}
