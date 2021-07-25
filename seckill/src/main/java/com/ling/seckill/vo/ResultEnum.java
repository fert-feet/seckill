package com.ling.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Ky2Fe on 2021/7/26 0:33
 */

/**
 * @program: seckill
 * @description: 统一返回类枚举
 **/

@Getter
@ToString
@AllArgsConstructor
public enum ResultEnum {

    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常")
    ;


    private final Integer code;
    private final String message;
}
