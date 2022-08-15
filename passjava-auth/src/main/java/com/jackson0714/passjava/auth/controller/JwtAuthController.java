package com.jackson0714.passjava.auth.controller;

import com.jackson0714.passjava.common.utils.SecurityUtils;
import com.jackson0714.passjava.jwt.common.ResponseCodeEnum;
import com.jackson0714.passjava.jwt.config.PassJavaJwtProperties;
import com.jackson0714.passjava.auth.jpa.SysUser;
import com.jackson0714.passjava.auth.jpa.SysUserRepository;
import com.jackson0714.passjava.jwt.utils.PassJavaJwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import reactor.core.publisher.Mono;
import com.jackson0714.passjava.jwt.common.ResponseResult;

/**
 * 获取 JWT 令牌和刷新 JWT 令牌接口
 *
 * @author 悟空聊架构
 * @site www.passjava.cn
 * @date 2022-08-09
 *
 *  @startuml
 *  @enduml
 */
@RestController
@RequestMapping("/auth")
//@ConditionalOnProperty(name = "passjava.gateway.jwt.useDefaultController", havingValue = "true")
public class JwtAuthController {

    @Resource
    private PassJavaJwtProperties jwtProperties;
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private PassJavaJwtTokenUtil jwtTokenUtil;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 使用用户名密码换 JWT 令牌
     */
    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody Map<String,String> map){
        // 从请求体中获取用户名密码
        String userId  = map.get(jwtProperties.getUserParamName());
        String password = map.get(jwtProperties.getPwdParamName());

        // 如果用户名和密码为空
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(password)){
            return ResponseResult.error(ResponseCodeEnum.LOGIN_ERROR.getCode(), ResponseCodeEnum.LOGIN_ERROR.getMessage());
        }
        // 根据 userId 去数据库查找该用户
        SysUser sysUser = sysUserRepository.findByUserId(userId);
        if(sysUser != null){
            // 将数据库的加密密码与用户明文密码做比对
            boolean isAuthenticated = passwordEncoder.matches(password,
                    sysUser.getPassword());
            // 如果密码匹配成功
            if(isAuthenticated){
                // 通过 jwtTokenUtil 生成 JWT 令牌和刷新令牌
                Map<String, Object> tokenMap = jwtTokenUtil
                        .generateTokenAndRefreshToken(userId, sysUser.getUsername());
                return ResponseResult.success(tokenMap);
            }
            // 如果密码匹配失败
            return ResponseResult.error(ResponseCodeEnum.LOGIN_ERROR.getCode(), ResponseCodeEnum.LOGIN_ERROR.getMessage());
        }
        // 如果未找到用户
        return ResponseResult.error(ResponseCodeEnum.LOGIN_ERROR.getCode(), ResponseCodeEnum.LOGIN_ERROR.getMessage());
    }

    /**
     * 刷新JWT令牌,用旧的令牌换新的令牌
     */
    @GetMapping("/refreshtoken")
    public  Mono<ResponseResult> refreshToken(@RequestHeader("${passjava.jwt.header}") String token){
        token = SecurityUtils.replaceTokenPrefix(token);

        if (StringUtils.isEmpty(token)) {
            return buildErrorResponse(ResponseCodeEnum.TOKEN_MISSION);
        }

        // 对Token解签名，并验证Token是否过期
        boolean isJwtNotValid = jwtTokenUtil.isTokenExpired(token);
        if(isJwtNotValid){
            return buildErrorResponse(ResponseCodeEnum.TOKEN_INVALID);
        }
        // 验证 token 里面的 userId 是否为空

        String userId = jwtTokenUtil.getUserIdFromToken(token);
        String username = jwtTokenUtil.getUserNameFromToken(token);
        if (StringUtils.isEmpty(userId)) {
            return buildErrorResponse(ResponseCodeEnum.TOKEN_CHECK_INFO_FAILED);
        }

        // 这里为了保证 refreshToken 只能用一次，刷新后，会从 redis 中删除。
        // 如果用的不是 redis 中的 refreshToken 进行刷新令牌，则不能刷新。
        // 如果使用 redis 中已过期的 refreshToken 也不能刷新令牌。
        boolean isRefreshTokenNotExisted = jwtTokenUtil.isRefreshTokenNotExistCache(token);
        if(isRefreshTokenNotExisted){
            return buildErrorResponse(ResponseCodeEnum.REFRESH_TOKEN_INVALID);
        }

        String us = jwtTokenUtil.getUserIdFromToken(token);
        Map<String, Object> tokenMap = jwtTokenUtil.refreshTokenAndGenerateToken(userId, username);

        return buildSuccessResponse(tokenMap);
    }

    /**
     * 登出，删除 redis 中的 accessToken 和 refreshToken
     * 只保证 refreshToken 不能使用，accessToken 还是能使用的。
     * 如果用户拿到了之前的 accessToken，则可以一直使用到过期，但是因为 refreshToken 已经无法使用了，所以保证了 accessToken 的时效性。
     * 下次登录时，需要重新获取新的 accessToken 和 refreshToken，这样才能利用 refreshToken 进行续期。
     */
    @PostMapping("/logout")
    public  Mono<ResponseResult> logout(@RequestParam("username") String username){

        boolean logoutResult = jwtTokenUtil.removeToken(username);
        if (logoutResult) {
            buildSuccessResponse(ResponseCodeEnum.SUCCESS);
        } else {
            buildErrorResponse(ResponseCodeEnum.LOGOUT_ERROR);
        }

        return buildSuccessResponse(ResponseCodeEnum.SUCCESS);
    }

    private Mono<ResponseResult> buildErrorResponse(ResponseCodeEnum responseCodeEnum){
        return Mono.create(callback -> callback.success(
                ResponseResult.error(responseCodeEnum.getCode(), responseCodeEnum.getMessage())
        ));
    }

    private Mono<ResponseResult> buildSuccessResponse(Object data){
        return Mono.create(callback -> callback.success(ResponseResult.success(data)
        ));
    }

}
