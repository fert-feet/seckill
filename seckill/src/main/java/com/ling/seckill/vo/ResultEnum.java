package com.ling.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Ky2Fe on 2021/7/26 0:33
 */

/**
 * @author Ky2Fe
 * @program: seckill
 * @description: 统一返回类枚举
 **/

@Getter
@ToString
@AllArgsConstructor
public enum ResultEnum {

    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //登录模块5002xx
    LOGIN_ERROR(500210, "用户名或密码不正确"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),
    ;


    private final Integer code;
    private final String message;
}
