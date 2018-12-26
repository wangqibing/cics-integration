package com.ccic.enums;

import com.alibaba.fastjson.serializer.IntegerCodec;
import lombok.Getter;

/**
 * Created by 8000600758 on 2018/10/7.
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
    ERROR(1,"失败"),
    USER_LOG_ERROR(2,"用户名或密码错误"),
    USER_NOT_EXIST(3,"用户不存在"),
    USER_INSERT_ERROR(4,"用户新增失败"),
    TOKEN_ERROR(5,"token不正确"),
    TOKEN_INVALID(6,"token失效"),
    TOKEN_SUCCESS(7,"token验证通过");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
