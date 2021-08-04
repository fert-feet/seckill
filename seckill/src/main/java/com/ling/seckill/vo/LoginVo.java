package com.ling.seckill.vo;/**
 * Created by Ky2Fe on 2021/7/26 16:37
 */

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Ky2Fe
 * @program: seckill
 * @description: 登录
 **/

@Data
public class LoginVo {
    @NotNull
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
