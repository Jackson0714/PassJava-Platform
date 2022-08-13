package com.jackson0714.passjava.jwt.common;

public enum ResponseCodeEnum {
    /**
     *
     */
    SUCCESS("0", "成功"),
    FAIL("-1", "失败"),
    LOGIN_ERROR("A1000", "用户名或密码错误"),
    LOGIN_EMPTY_ERROR("A1001", "用户名或者密码不能为空"),

    UNKNOWN_ERROR("B2000", "未知错误"),
    PARAMETER_ILLEGAL("B2001", "参数不合法"),

    TOKEN_INVALID("B2002", "token 已过期或验证不正确！"),
    TOKEN_SIGNATURE_INVALID("B2003", "无效的签名"),
    TOKEN_EXPIRED("B2004", "token 已过期"),
    TOKEN_MISSION("B2005", "token 缺失"),
    TOKEN_CHECK_INFO_FAILED("B2006", "token 信息验证失败"),
    REFRESH_TOKEN_INVALID("B2006", "refreshToken 无效"),
    LOGOUT_ERROR("B2007", "用户登出失败");
    private final String code;
    private final String message;
    ResponseCodeEnum(String code, String message) {
    this.code = code;
    this.message = message;
    }
    public String getCode() {
    return code;
    }
    public String getMessage() {
    return message;
    }
}
