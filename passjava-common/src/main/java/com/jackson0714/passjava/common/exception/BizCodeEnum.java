package com.jackson0714.passjava.common.exception;

/***
 * .错误码和错误信息定义类
 *
 * - 1.错误码长度：5个数字
 * - 2.前两位：业务场景
 * - 3.后三位：错误码
 *
 *     10：通用业务
 *     	001：参数格式校验错误（10001）
 *     11：会员业务
 *     12：题目业务
 *     13：内容业务
 *     14：学习业务
 ***/
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    QUESTION_SAVE_EXCEPTION(12001, "题目保存异常");

    private int code;
    private String msg;
    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
