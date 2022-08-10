package com.jackson0714.passjava.gateway.controller;

import com.jackson0714.passjava.gateway.common.ResponseCodeEnum;
import com.jackson0714.passjava.gateway.config.PassJavaJwtProperties;
import com.jackson0714.passjava.gateway.jpa.SysUser;
import com.jackson0714.passjava.gateway.jpa.SysUserRepository;
import com.jackson0714.passjava.gateway.utils.PassJavaJwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import reactor.core.publisher.Mono;
import com.jackson0714.passjava.gateway.common.ResponseResult;

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
@ConditionalOnProperty(name = "passjava.gateway.jwt.useDefaultController", havingValue = "true")
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
    @RequestMapping("/token")
    public Mono<ResponseResult> authentication(@RequestBody Map<String,String> map){
        //从请求体中获取用户名密码
        String username  = map.get(jwtProperties.getUserParamName());
        String password = map.get(jwtProperties.getPwdParamName());

        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            return buildErrorResponse(ResponseCodeEnum.LOGIN_ERROR);
        }
        //根据用户名（用户Id）去数据库查找该用户
        SysUser sysUser = sysUserRepository.findByUsername(username);
        if(sysUser != null){
            //将数据库的加密密码与用户明文密码match
            boolean isAuthenticated = passwordEncoder.matches(password, sysUser.getPassword());
            //如果匹配成功
            if(isAuthenticated){
                //通过 jwtTokenUtil 生成JWT令牌并return
                return buildSuccessResponse(jwtTokenUtil.generateToken(username,null));
            } else{ //如果密码匹配失败
                return buildErrorResponse(ResponseCodeEnum.LOGIN_ERROR);
            }
        }else{
            return buildErrorResponse(ResponseCodeEnum.LOGIN_ERROR);
        }
    }

    /**
     * 刷新JWT令牌,用旧的令牌换新的令牌
     */
    @RequestMapping("/refreshtoken")
    public  Mono<ResponseResult> refreshtoken(@RequestHeader("${passjava.gateway.jwt.header}") String oldToken){
        if(!jwtTokenUtil.isTokenExpired(oldToken)){
            return buildSuccessResponse(jwtTokenUtil.refreshToken(oldToken));
        }
        return Mono.empty();
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
